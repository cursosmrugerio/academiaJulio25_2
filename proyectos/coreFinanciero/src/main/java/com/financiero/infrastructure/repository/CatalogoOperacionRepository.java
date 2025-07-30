package com.financiero.infrastructure.repository;

import com.financiero.domain.entity.CatalogoOperacion;
import com.financiero.domain.entity.CatalogoOperacionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CatalogoOperacionRepository extends JpaRepository<CatalogoOperacion, CatalogoOperacionId> {
    
    @Query("SELECT co FROM CatalogoOperacion co WHERE co.id.claveGrupoEmpresa = :claveGrupoEmpresa AND co.id.claveEmpresa = :claveEmpresa AND co.id.claveOperacion = :claveOperacion")
    Optional<CatalogoOperacion> findByEmpresaAndOperacion(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("claveOperacion") String claveOperacion);
    
    @Query("SELECT co FROM CatalogoOperacion co WHERE co.id.claveGrupoEmpresa = :claveGrupoEmpresa AND co.id.claveEmpresa = :claveEmpresa AND co.estatusOperacion = :estatus")
    List<CatalogoOperacion> findByEmpresaAndEstatus(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("estatus") String estatus);
    
    @Query("SELECT co FROM CatalogoOperacion co WHERE co.id.claveGrupoEmpresa = :claveGrupoEmpresa AND co.id.claveEmpresa = :claveEmpresa AND co.claveAfectaSaldo = :afectaSaldo")
    List<CatalogoOperacion> findByEmpresaAndAfectaSaldo(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("afectaSaldo") String afectaSaldo);
    
    @Query("SELECT co FROM CatalogoOperacion co WHERE co.id.claveGrupoEmpresa = :claveGrupoEmpresa AND co.id.claveEmpresa = :claveEmpresa AND co.estatusOperacion = 'A'")
    List<CatalogoOperacion> findOperacionesActivas(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa);
    
    @Query("SELECT co FROM CatalogoOperacion co WHERE co.id.claveGrupoEmpresa = :claveGrupoEmpresa AND co.id.claveEmpresa = :claveEmpresa AND co.claveAfectaSaldo IN ('I', 'D') AND co.estatusOperacion = 'A'")
    List<CatalogoOperacion> findOperacionesQueAfectanSaldo(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa);
}