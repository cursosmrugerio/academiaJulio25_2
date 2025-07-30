package com.financiero.infrastructure.repository;

import com.financiero.domain.entity.Saldo;
import com.financiero.domain.entity.SaldoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaldoRepository extends JpaRepository<Saldo, SaldoId> {
    
    @Query("SELECT s FROM Saldo s WHERE s.id.claveGrupoEmpresa = :claveGrupoEmpresa AND s.id.claveEmpresa = :claveEmpresa AND s.id.fechaFoto = :fechaFoto AND s.id.idCuenta = :idCuenta AND s.id.claveDivisa = :claveDivisa")
    Optional<Saldo> findByCuentaFechaDivisa(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("fechaFoto") LocalDate fechaFoto,
        @Param("idCuenta") Long idCuenta,
        @Param("claveDivisa") String claveDivisa);
    
    @Query("SELECT s FROM Saldo s WHERE s.id.claveGrupoEmpresa = :claveGrupoEmpresa AND s.id.claveEmpresa = :claveEmpresa AND s.id.idCuenta = :idCuenta AND s.id.claveDivisa = :claveDivisa ORDER BY s.id.fechaFoto DESC")
    List<Saldo> findByCuentaDivisaOrderByFechaDesc(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("idCuenta") Long idCuenta,
        @Param("claveDivisa") String claveDivisa);
    
    @Query("SELECT s FROM Saldo s WHERE s.id.claveGrupoEmpresa = :claveGrupoEmpresa AND s.id.claveEmpresa = :claveEmpresa AND s.id.idCuenta = :idCuenta AND s.id.claveDivisa = :claveDivisa AND s.id.fechaFoto <= :fecha ORDER BY s.id.fechaFoto DESC")
    Optional<Saldo> findUltimoSaldoAntesDeFecha(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("idCuenta") Long idCuenta,
        @Param("claveDivisa") String claveDivisa,
        @Param("fecha") LocalDate fecha);
    
    @Query("SELECT s FROM Saldo s WHERE s.id.claveGrupoEmpresa = :claveGrupoEmpresa AND s.id.claveEmpresa = :claveEmpresa AND s.id.fechaFoto = :fechaFoto")
    List<Saldo> findByEmpresaAndFecha(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("fechaFoto") LocalDate fechaFoto);
    
    @Query("SELECT s FROM Saldo s WHERE s.id.claveGrupoEmpresa = :claveGrupoEmpresa AND s.id.claveEmpresa = :claveEmpresa AND s.id.idCuenta = :idCuenta AND s.id.fechaFoto BETWEEN :fechaInicio AND :fechaFin")
    List<Saldo> findByCuentaEnRangoFechas(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("idCuenta") Long idCuenta,
        @Param("fechaInicio") LocalDate fechaInicio,
        @Param("fechaFin") LocalDate fechaFin);
    
    @Query("SELECT s FROM Saldo s WHERE s.id.claveGrupoEmpresa = :claveGrupoEmpresa AND s.id.claveEmpresa = :claveEmpresa AND s.saldoEfectivo < :importe")
    List<Saldo> findSaldosMenoresA(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("importe") BigDecimal importe);
    
    @Query("SELECT DISTINCT s.id.claveDivisa FROM Saldo s WHERE s.id.claveGrupoEmpresa = :claveGrupoEmpresa AND s.id.claveEmpresa = :claveEmpresa AND s.id.idCuenta = :idCuenta")
    List<String> findDivisasByCuenta(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("idCuenta") Long idCuenta);
}