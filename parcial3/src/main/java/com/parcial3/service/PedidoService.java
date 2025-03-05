package com.parcial3.service;

import com.parcial3.model.Pedido;
import com.parcial3.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import java.time.LocalDateTime;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Mono<Pedido> crearPedido(Pedido pedido) {
        if (pedido.getFecha() == null) {
            pedido.setFecha(LocalDateTime.now());
        }
        return Mono.fromCallable(() -> pedidoRepository.save(pedido))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Flux<Pedido> listarPedidos() {
        return Flux.defer(() -> Flux.fromIterable(pedidoRepository.findAll()))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
