package com.financiero.infrastructure.repository;

import com.financiero.domain.entity.DiaFestivo;
import com.financiero.domain.entity.DiaFestivoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaFestivoRepository extends JpaRepository<DiaFestivo, DiaFestivoId> {
    
    Optional<DiaFestivo> findByIdClaveGrupoEmpresaAndIdClaveEmpresaAndIdClavePaisAndIdFechaDiaFestivo(
        String claveGrupoEmpresa, String claveEmpresa, String clavePais, LocalDate fechaDiaFestivo);
    
    @Query("SELECT d FROM DiaFestivo d WHERE d.id.claveGrupoEmpresa = :claveGrupoEmpresa " +
           "AND d.id.claveEmpresa = :claveEmpresa AND d.id.clavePais = 'MX' " +
           "AND d.id.fechaDiaFestivo = :fecha")
    Optional<DiaFestivo> findDiaFestivoMexico(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("fecha") LocalDate fecha);
    
    @Query("SELECT d.id.fechaDiaFestivo FROM DiaFestivo d WHERE d.id.claveGrupoEmpresa = :claveGrupoEmpresa " +
           "AND d.id.claveEmpresa = :claveEmpresa AND d.id.clavePais = :clavePais " +
           "AND YEAR(d.id.fechaDiaFestivo) = :anio")
    List<LocalDate> findFechasFestivasByAnio(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("clavePais") String clavePais,
        @Param("anio") int anio);
    
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM DiaFestivo d " +
           "WHERE d.id.claveGrupoEmpresa = :claveGrupoEmpresa AND d.id.claveEmpresa = :claveEmpresa " +
           "AND d.id.clavePais = 'MX' AND d.id.fechaDiaFestivo = :fecha")
    boolean esDiaFestivo(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("fecha") LocalDate fecha);
}