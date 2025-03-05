package com.parcial3.controller;

import com.parcial3.model.Pedido;
import com.parcial3.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Locale;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public Flux<Pedido> listarPedidos(@RequestParam(value = "lang", required = false) String lang) {
        return pedidoService.listarPedidos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<String> crearPedido(@RequestBody Pedido pedido,
                                    @RequestParam(value = "lang", required = false) String lang) {
        Locale locale = Locale.ENGLISH;
        if (lang != null && !lang.isEmpty()) {
            locale = Locale.forLanguageTag(lang);
        }
        final Locale finalLocale = locale;
        return pedidoService.crearPedido(pedido)
                .map(p -> messageSource.getMessage("pedido.creado", new Object[]{p.getId()}, finalLocale));
    }
}