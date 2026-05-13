package com.duoc.ms_pedidos.service;

import com.duoc.ms_pedidos.dto.ApiResponse;
import com.duoc.ms_pedidos.dto.PedidoDTO;
import com.duoc.ms_pedidos.dto.PacienteResumenDTO;
import com.duoc.ms_pedidos.dto.PedidoResponse;
import com.duoc.ms_pedidos.model.Pedido;
import com.duoc.ms_pedidos.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository repo;
    private final WebClient webClient = WebClient.create("http://localhost:8083");

    public List<PedidoResponse> listar() {
        log.info("Listar pedidos");
        return repo.findAll().stream().map(this::mapToResponse).toList();
    }

    public PedidoResponse obtener(Long id) {
        log.info("Obtener pedidos id: {}", id);
        return mapToResponse(obtenerEntity(id));
    }

    public PedidoResponse crear(PedidoDTO dto) {
        log.info("Crear pedidos");
        validarPaciente(dto.getPacienteRut());

        Pedido item = new Pedido();
        item.setNumeroPedido(dto.getNumeroPedido());
        item.setPacienteRut(dto.getPacienteRut());
        item.setTotal(dto.getTotal());
        item.setEstado(dto.getEstado());
        return mapToResponse(repo.save(item));
    }

    public PedidoResponse actualizar(Long id, PedidoDTO dto) {
        log.info("Actualizar pedidos id: {}", id);
        validarPaciente(dto.getPacienteRut());

        Pedido item = obtenerEntity(id);
        item.setNumeroPedido(dto.getNumeroPedido());
        item.setPacienteRut(dto.getPacienteRut());
        item.setTotal(dto.getTotal());
        item.setEstado(dto.getEstado());
        return mapToResponse(repo.save(item));
    }

    public void eliminar(Long id) {
        log.warn("Eliminar pedidos id: {}", id);
        Pedido item = obtenerEntity(id);
        repo.delete(item);
    }

    private Pedido obtenerEntity(Long id) {
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));
    }

    private void validarPaciente(String rut) {
        obtenerPacientePorRut(rut);
    }

    private PedidoResponse mapToResponse(Pedido pedido) {
        return PedidoResponse.builder()
                .id(pedido.getId())
                .numeroPedido(pedido.getNumeroPedido())
                .total(pedido.getTotal())
                .estado(pedido.getEstado())
                .paciente(obtenerPacientePorRut(pedido.getPacienteRut()))
                .build();
    }

    private PacienteResumenDTO obtenerPacientePorRut(String rut) {
        ApiResponse<PacienteResumenDTO> response = webClient.get()
                .uri("/api/pacientes/rut/" + rut)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<PacienteResumenDTO>>() {})
                .block();

        if (response == null || response.getData() == null) {
            throw new RuntimeException("Paciente no existe");
        }

        return response.getData();
    }
}
