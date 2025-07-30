package com.financiero.infrastructure.repository;

import com.financiero.domain.entity.Movimiento;
import com.financiero.domain.entity.MovimientoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, MovimientoId> {
    
    @Query("SELECT m FROM Movimiento m WHERE m.id.claveGrupoEmpresa = :claveGrupoEmpresa AND m.id.claveEmpresa = :claveEmpresa AND m.situacionMovimiento = :situacion")
    List<Movimiento> findByEmpresaAndSituacion(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("situacion") String situacion);
    
    @Query("SELECT m FROM Movimiento m WHERE m.id.claveGrupoEmpresa = :claveGrupoEmpresa AND m.id.claveEmpresa = :claveEmpresa AND m.fechaOperacion = :fecha")
    List<Movimiento> findByEmpresaAndFechaOperacion(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("fecha") LocalDate fecha);
    
    @Query("SELECT m FROM Movimiento m WHERE m.id.claveGrupoEmpresa = :claveGrupoEmpresa AND m.id.claveEmpresa = :claveEmpresa AND m.fechaLiquidacion = :fecha")
    List<Movimiento> findByEmpresaAndFechaLiquidacion(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("fecha") LocalDate fecha);
    
    @Query("SELECT m FROM Movimiento m WHERE m.idCuenta = :idCuenta AND m.fechaOperacion BETWEEN :fechaInicio AND :fechaFin")
    List<Movimiento> findByCuentaAndRangoFechas(
        @Param("idCuenta") Long idCuenta,
        @Param("fechaInicio") LocalDate fechaInicio,
        @Param("fechaFin") LocalDate fechaFin);
    
    @Query("SELECT m FROM Movimiento m WHERE m.idPrestamo = :idPrestamo")
    List<Movimiento> findByPrestamo(@Param("idPrestamo") Long idPrestamo);
    
    @Query("SELECT m FROM Movimiento m WHERE m.idPreMovimiento = :idPreMovimiento")
    Optional<Movimiento> findByIdPreMovimiento(@Param("idPreMovimiento") Long idPreMovimiento);
    
    @Query("SELECT m FROM Movimiento m WHERE m.id.claveGrupoEmpresa = :claveGrupoEmpresa AND m.id.claveEmpresa = :claveEmpresa AND m.situacionMovimiento IN ('NP', 'PV')")
    List<Movimiento> findPendientesProcesamiento(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa);
    
    @Query("SELECT COALESCE(MAX(m.id.idMovimiento), 0) FROM Movimiento m WHERE m.id.claveGrupoEmpresa = :claveGrupoEmpresa AND m.id.claveEmpresa = :claveEmpresa")
    Long findMaxIdMovimiento(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa);
}