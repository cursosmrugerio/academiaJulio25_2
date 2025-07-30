package com.financiero.infrastructure.repository;

import com.financiero.domain.entity.PreMovimiento;
import com.financiero.domain.entity.PreMovimientoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PreMovimientoRepository extends JpaRepository<PreMovimiento, PreMovimientoId> {
    
    List<PreMovimiento> findByIdClaveGrupoEmpresaAndIdClaveEmpresaAndFechaOperacion(
        String claveGrupoEmpresa, String claveEmpresa, LocalDate fechaOperacion);
    
    List<PreMovimiento> findByIdClaveGrupoEmpresaAndIdClaveEmpresaAndSituacionPreMovimiento(
        String claveGrupoEmpresa, String claveEmpresa, String situacionPreMovimiento);
    
    @Query("SELECT p FROM PreMovimiento p WHERE p.id.claveGrupoEmpresa = :claveGrupoEmpresa " +
           "AND p.id.claveEmpresa = :claveEmpresa AND p.idPrestamo = :idPrestamo")
    List<PreMovimiento> findByEmpresaAndPrestamo(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("idPrestamo") Long idPrestamo);
    
    @Query("SELECT p FROM PreMovimiento p WHERE p.id.claveGrupoEmpresa = :claveGrupoEmpresa " +
           "AND p.id.claveEmpresa = :claveEmpresa AND p.fechaLiquidacion = :fechaLiquidacion " +
           "AND p.situacionPreMovimiento = 'NP'")
    List<PreMovimiento> findPendientesByFechaLiquidacion(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("fechaLiquidacion") LocalDate fechaLiquidacion);
    
    @Query("SELECT p FROM PreMovimiento p WHERE p.id.claveGrupoEmpresa = :claveGrupoEmpresa " +
           "AND p.id.claveEmpresa = :claveEmpresa AND p.claveMercado = :claveMercado " +
           "AND p.fechaOperacion BETWEEN :fechaInicio AND :fechaFin")
    List<PreMovimiento> findByMercadoAndRangoFechas(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("claveMercado") String claveMercado,
        @Param("fechaInicio") LocalDate fechaInicio,
        @Param("fechaFin") LocalDate fechaFin);
    
    @Query("SELECT COUNT(p) FROM PreMovimiento p WHERE p.id.claveGrupoEmpresa = :claveGrupoEmpresa " +
           "AND p.id.claveEmpresa = :claveEmpresa AND p.fechaOperacion = :fechaOperacion")
    long countByFechaOperacion(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("fechaOperacion") LocalDate fechaOperacion);
    
    Optional<PreMovimiento> findByIdClaveGrupoEmpresaAndIdClaveEmpresaAndIdIdPreMovimiento(
        String claveGrupoEmpresa, String claveEmpresa, Long idPreMovimiento);
}