package com.financiero.infrastructure.repository;

import com.financiero.domain.entity.PreMovimientoDetalle;
import com.financiero.domain.entity.PreMovimientoDetalleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PreMovimientoDetalleRepository extends JpaRepository<PreMovimientoDetalle, PreMovimientoDetalleId> {
    
    List<PreMovimientoDetalle> findByIdClaveGrupoEmpresaAndIdClaveEmpresaAndIdIdPreMovimiento(
        String claveGrupoEmpresa, String claveEmpresa, Long idPreMovimiento);
    
    List<PreMovimientoDetalle> findByIdClaveGrupoEmpresaAndIdClaveEmpresaAndIdClaveConcepto(
        String claveGrupoEmpresa, String claveEmpresa, String claveConcepto);
    
    @Query("SELECT d FROM PreMovimientoDetalle d WHERE d.id.claveGrupoEmpresa = :claveGrupoEmpresa " +
           "AND d.id.claveEmpresa = :claveEmpresa AND d.id.idPreMovimiento = :idPreMovimiento " +
           "AND d.id.claveConcepto = :claveConcepto")
    PreMovimientoDetalle findByPreMovimientoAndConcepto(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("idPreMovimiento") Long idPreMovimiento,
        @Param("claveConcepto") String claveConcepto);
    
    @Query("SELECT SUM(d.importeConcepto) FROM PreMovimientoDetalle d WHERE d.id.claveGrupoEmpresa = :claveGrupoEmpresa " +
           "AND d.id.claveEmpresa = :claveEmpresa AND d.id.idPreMovimiento = :idPreMovimiento")
    BigDecimal sumImporteConceptoByPreMovimiento(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("idPreMovimiento") Long idPreMovimiento);
    
    @Query("SELECT COUNT(d) FROM PreMovimientoDetalle d WHERE d.id.claveGrupoEmpresa = :claveGrupoEmpresa " +
           "AND d.id.claveEmpresa = :claveEmpresa AND d.id.idPreMovimiento = :idPreMovimiento")
    long countByPreMovimiento(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("idPreMovimiento") Long idPreMovimiento);
}