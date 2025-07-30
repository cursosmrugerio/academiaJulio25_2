package com.financiero.infrastructure.repository;

import com.financiero.domain.entity.DiaLiquidacion;
import com.financiero.domain.entity.DiaLiquidacionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaLiquidacionRepository extends JpaRepository<DiaLiquidacion, DiaLiquidacionId> {
    
    List<DiaLiquidacion> findByIdClaveGrupoEmpresaAndIdClaveEmpresaAndIdFechaInformacion(
        String claveGrupoEmpresa, String claveEmpresa, LocalDate fechaInformacion);
    
    @Query("SELECT d FROM DiaLiquidacion d WHERE d.id.claveGrupoEmpresa = :claveGrupoEmpresa " +
           "AND d.id.claveEmpresa = :claveEmpresa AND d.id.fechaInformacion = :fechaInformacion " +
           "AND d.id.claveLiquidacion LIKE 'T+%' AND d.id.claveLiquidacion <> 'T+0' " +
           "ORDER BY d.fechaLiquidacion ASC")
    List<DiaLiquidacion> findFechasLiquidacionT(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("fechaInformacion") LocalDate fechaInformacion);
    
    @Query("SELECT d FROM DiaLiquidacion d WHERE d.id.claveGrupoEmpresa = :claveGrupoEmpresa " +
           "AND d.id.claveEmpresa = :claveEmpresa AND d.id.claveLiquidacion IN :tiposLiquidacion " +
           "AND d.id.fechaInformacion = :fechaOperacion AND d.fechaLiquidacion = :fechaLiquidacion")
    Optional<DiaLiquidacion> findByTipoLiquidacionAndFechas(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("tiposLiquidacion") List<String> tiposLiquidacion,
        @Param("fechaOperacion") LocalDate fechaOperacion,
        @Param("fechaLiquidacion") LocalDate fechaLiquidacion);
    
    @Query("SELECT d FROM DiaLiquidacion d WHERE d.id.claveGrupoEmpresa = :claveGrupoEmpresa " +
           "AND d.id.claveEmpresa = :claveEmpresa AND d.id.claveLiquidacion = 'T+1' " +
           "AND d.id.fechaInformacion = :fechaHoy")
    Optional<DiaLiquidacion> findFechaT001(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("fechaHoy") LocalDate fechaHoy);
    
    @Query("SELECT MAX(d.id.fechaInformacion) FROM DiaLiquidacion d WHERE d.id.claveGrupoEmpresa = :claveGrupoEmpresa " +
           "AND d.id.claveEmpresa = :claveEmpresa AND d.id.fechaInformacion >= :fechaInicio " +
           "AND d.id.claveLiquidacion = 'T+0'")
    Optional<LocalDate> findMaxFechaInformacion(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("fechaInicio") LocalDate fechaInicio);
    
    @Modifying
    @Query("DELETE FROM DiaLiquidacion d WHERE d.id.claveGrupoEmpresa = :claveGrupoEmpresa " +
           "AND d.id.claveEmpresa = :claveEmpresa AND d.id.fechaInformacion > :fechaCorte")
    void deleteByFechaInformacionGreaterThan(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("fechaCorte") LocalDate fechaCorte);
    
    @Modifying
    @Query("DELETE FROM DiaLiquidacion d WHERE d.id.claveGrupoEmpresa = :claveGrupoEmpresa " +
           "AND d.id.claveEmpresa = :claveEmpresa AND d.id.claveLiquidacion = :claveLiquidacion " +
           "AND d.id.fechaInformacion >= :fechaInicio")
    void deleteByClaveLiquidacionAndFechaInformacion(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("claveLiquidacion") String claveLiquidacion,
        @Param("fechaInicio") LocalDate fechaInicio);
}