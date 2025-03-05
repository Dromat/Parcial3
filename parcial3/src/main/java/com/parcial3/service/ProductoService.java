package com.parcial3.service;

import com.parcial3.model.Producto;
import com.parcial3.repository.ProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Flux<Producto> obtenerProductos() {
        return Flux.defer(() -> Flux.fromIterable(productoRepository.findAll()))
                .subscribeOn(Schedulers.boundedElastic());
    }
}