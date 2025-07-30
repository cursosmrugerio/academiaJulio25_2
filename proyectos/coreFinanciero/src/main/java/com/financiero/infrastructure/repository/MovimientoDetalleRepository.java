package com.financiero.infrastructure.repository;

import com.financiero.domain.entity.MovimientoDetalle;
import com.financiero.domain.entity.MovimientoDetalleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoDetalleRepository extends JpaRepository<MovimientoDetalle, MovimientoDetalleId> {
    
    @Query("SELECT md FROM MovimientoDetalle md WHERE md.id.claveGrupoEmpresa = :claveGrupoEmpresa AND md.id.claveEmpresa = :claveEmpresa AND md.id.idMovimiento = :idMovimiento")
    List<MovimientoDetalle> findByMovimiento(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("idMovimiento") Long idMovimiento);
    
    @Query("SELECT md FROM MovimientoDetalle md WHERE md.id.claveGrupoEmpresa = :claveGrupoEmpresa AND md.id.claveEmpresa = :claveEmpresa AND md.id.idMovimiento = :idMovimiento AND md.id.claveConcepto = :claveConcepto")
    MovimientoDetalle findByMovimientoAndConcepto(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("idMovimiento") Long idMovimiento,
        @Param("claveConcepto") String claveConcepto);
    
    @Query("SELECT md FROM MovimientoDetalle md WHERE md.id.claveConcepto = :claveConcepto")
    List<MovimientoDetalle> findByConcepto(@Param("claveConcepto") String claveConcepto);
    
    @Query("SELECT SUM(md.importeConcepto) FROM MovimientoDetalle md WHERE md.id.claveGrupoEmpresa = :claveGrupoEmpresa AND md.id.claveEmpresa = :claveEmpresa AND md.id.idMovimiento = :idMovimiento")
    java.math.BigDecimal getTotalImportesByMovimiento(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("idMovimiento") Long idMovimiento);
}