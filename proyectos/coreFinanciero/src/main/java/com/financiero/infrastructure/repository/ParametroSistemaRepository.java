package com.financiero.infrastructure.repository;

import com.financiero.domain.entity.ParametroSistema;
import com.financiero.domain.entity.ParametroSistemaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ParametroSistemaRepository extends JpaRepository<ParametroSistema, ParametroSistemaId> {
    
    Optional<ParametroSistema> findByIdClaveGrupoEmpresaAndIdClaveEmpresaAndIdClaveMedio(
        String claveGrupoEmpresa, String claveEmpresa, String claveMedio);
    
    @Query("SELECT p FROM ParametroSistema p WHERE p.id.claveGrupoEmpresa = :claveGrupoEmpresa " +
           "AND p.id.claveEmpresa = :claveEmpresa AND p.id.claveMedio = 'SYSTEM'")
    Optional<ParametroSistema> findParametroSistema(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa);
    
    @Query("SELECT p.fechaMedio FROM ParametroSistema p WHERE p.id.claveGrupoEmpresa = :claveGrupoEmpresa " +
           "AND p.id.claveEmpresa = :claveEmpresa AND p.id.claveMedio = 'SYSTEM'")
    Optional<LocalDate> findFechaSistema(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa);
}