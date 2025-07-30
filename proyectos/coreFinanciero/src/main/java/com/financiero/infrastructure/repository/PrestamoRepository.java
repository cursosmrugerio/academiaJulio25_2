package com.financiero.infrastructure.repository;

import com.financiero.domain.entity.Prestamo;
import com.financiero.domain.entity.PrestamoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, PrestamoId> {
    
    Optional<Prestamo> findByIdPrestamo(Long idPrestamo);
    
    List<Prestamo> findByIdClaveGrupoEmpresaAndIdClaveEmpresa(
        String claveGrupoEmpresa, String claveEmpresa);
    
    @Query("SELECT p FROM Prestamo p WHERE p.id.claveGrupoEmpresa = :claveGrupoEmpresa " +
           "AND p.id.claveEmpresa = :claveEmpresa AND p.claveDivisa = :claveDivisa")
    List<Prestamo> findByEmpresaAndDivisa(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa,
        @Param("claveDivisa") String claveDivisa);
    
    @Query("SELECT COUNT(p) FROM Prestamo p WHERE p.id.claveGrupoEmpresa = :claveGrupoEmpresa " +
           "AND p.id.claveEmpresa = :claveEmpresa")
    long countByEmpresa(
        @Param("claveGrupoEmpresa") String claveGrupoEmpresa,
        @Param("claveEmpresa") String claveEmpresa);
    
    boolean existsByIdPrestamo(Long idPrestamo);
}