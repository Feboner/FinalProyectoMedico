package com.duoc.ms_inventario.repository;

import com.duoc.ms_inventario.model.InventarioItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<InventarioItem, Long> {
}
