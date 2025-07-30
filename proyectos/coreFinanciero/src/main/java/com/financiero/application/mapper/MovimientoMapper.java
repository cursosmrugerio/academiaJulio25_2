package com.financiero.application.mapper;

import com.financiero.application.dto.*;
import com.financiero.domain.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovimientoMapper {
    
    public MovimientoDTO toDTO(Movimiento movimiento) {
        if (movimiento == null) {
            return null;
        }
        
        MovimientoDTO dto = new MovimientoDTO();
        
        if (movimiento.getId() != null) {
            dto.setClaveGrupoEmpresa(movimiento.getId().getClaveGrupoEmpresa());
            dto.setClaveEmpresa(movimiento.getId().getClaveEmpresa());
            dto.setIdMovimiento(movimiento.getId().getIdMovimiento());
        }
        
        dto.setIdCuenta(movimiento.getIdCuenta());
        dto.setClaveDivisa(movimiento.getClaveDivisa());
        dto.setFechaOperacion(movimiento.getFechaOperacion());
        dto.setFechaLiquidacion(movimiento.getFechaLiquidacion());
        dto.setFechaAplicacion(movimiento.getFechaAplicacion());
        dto.setClaveOperacion(movimiento.getClaveOperacion());
        dto.setImporteNeto(movimiento.getImporteNeto());
        dto.setPrecioOperacion(movimiento.getPrecioOperacion());
        dto.setTipoCambio(movimiento.getTipoCambio());
        dto.setIdReferencia(movimiento.getIdReferencia());
        dto.setIdPrestamo(movimiento.getIdPrestamo());
        dto.setClaveMercado(movimiento.getClaveMercado());
        dto.setClaveMedio(movimiento.getClaveMedio());
        dto.setTextoNota(movimiento.getNota());
        dto.setTextoReferencia(movimiento.getReferencia());
        dto.setIdGrupo(movimiento.getIdGrupo());
        dto.setIdPreMovimiento(movimiento.getIdPreMovimiento());
        dto.setSituacionMovimiento(movimiento.getSituacionMovimiento());
        dto.setFechaHoraRegistro(movimiento.getFechaHoraRegistro());
        dto.setFechaHoraActivacion(movimiento.getFechaHoraActivacion());
        dto.setLogIpAddress(movimiento.getLogIpAddress());
        dto.setLogOsUser(movimiento.getLogOsUser());
        dto.setLogHost(movimiento.getLogHost());
        dto.setClaveUsuario(movimiento.getClaveUsuario());
        dto.setClaveUsuarioCancela(movimiento.getClaveUsuarioCancela());
        dto.setNumeroPagoAmortizacion(movimiento.getNumeroPagoAmortizacion());
        
        return dto;
    }
    
    public Movimiento toEntity(MovimientoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Movimiento movimiento = new Movimiento();
        
        if (dto.getClaveGrupoEmpresa() != null && dto.getClaveEmpresa() != null && dto.getIdMovimiento() != null) {
            MovimientoId id = new MovimientoId(dto.getClaveGrupoEmpresa(), dto.getClaveEmpresa(), dto.getIdMovimiento());
            movimiento.setId(id);
        }
        
        movimiento.setIdCuenta(dto.getIdCuenta());
        movimiento.setClaveDivisa(dto.getClaveDivisa());
        movimiento.setFechaOperacion(dto.getFechaOperacion());
        movimiento.setFechaLiquidacion(dto.getFechaLiquidacion());
        movimiento.setFechaAplicacion(dto.getFechaAplicacion());
        movimiento.setClaveOperacion(dto.getClaveOperacion());
        movimiento.setImporteNeto(dto.getImporteNeto());
        movimiento.setPrecioOperacion(dto.getPrecioOperacion());
        movimiento.setTipoCambio(dto.getTipoCambio());
        movimiento.setIdReferencia(dto.getIdReferencia());
        movimiento.setIdPrestamo(dto.getIdPrestamo());
        movimiento.setClaveMercado(dto.getClaveMercado());
        movimiento.setClaveMedio(dto.getClaveMedio());
        movimiento.setNota(dto.getTextoNota());
        movimiento.setReferencia(dto.getTextoReferencia());
        movimiento.setIdGrupo(dto.getIdGrupo());
        movimiento.setIdPreMovimiento(dto.getIdPreMovimiento());
        movimiento.setSituacionMovimiento(dto.getSituacionMovimiento());
        movimiento.setFechaHoraRegistro(dto.getFechaHoraRegistro());
        movimiento.setFechaHoraActivacion(dto.getFechaHoraActivacion());
        movimiento.setLogIpAddress(dto.getLogIpAddress());
        movimiento.setLogOsUser(dto.getLogOsUser());
        movimiento.setLogHost(dto.getLogHost());
        movimiento.setClaveUsuario(dto.getClaveUsuario());
        movimiento.setClaveUsuarioCancela(dto.getClaveUsuarioCancela());
        movimiento.setNumeroPagoAmortizacion(dto.getNumeroPagoAmortizacion());
        
        return movimiento;
    }
    
    public List<MovimientoDTO> toDTOList(List<Movimiento> movimientos) {
        if (movimientos == null) {
            return null;
        }
        return movimientos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public MovimientoDetalleDTO toDTO(MovimientoDetalle detalle) {
        if (detalle == null) {
            return null;
        }
        
        MovimientoDetalleDTO dto = new MovimientoDetalleDTO();
        
        if (detalle.getId() != null) {
            dto.setClaveGrupoEmpresa(detalle.getId().getClaveGrupoEmpresa());
            dto.setClaveEmpresa(detalle.getId().getClaveEmpresa());
            dto.setIdMovimiento(detalle.getId().getIdMovimiento());
            dto.setClaveConcepto(detalle.getId().getClaveConcepto());
        }
        
        dto.setImporteConcepto(detalle.getImporteConcepto());
        dto.setTextoNota(detalle.getNota());
        
        return dto;
    }
    
    public SaldoDTO toDTO(Saldo saldo) {
        if (saldo == null) {
            return null;
        }
        
        SaldoDTO dto = new SaldoDTO();
        
        if (saldo.getId() != null) {
            dto.setClaveGrupoEmpresa(saldo.getId().getClaveGrupoEmpresa());
            dto.setClaveEmpresa(saldo.getId().getClaveEmpresa());
            dto.setFechaFoto(saldo.getId().getFechaFoto());
            dto.setIdCuenta(saldo.getId().getIdCuenta());
            dto.setClaveDivisa(saldo.getId().getClaveDivisa());
        }
        
        dto.setSaldoEfectivo(saldo.getSaldoEfectivo());
        
        return dto;
    }
    
    public List<SaldoDTO> toSaldoDTOList(List<Saldo> saldos) {
        if (saldos == null) {
            return null;
        }
        return saldos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public CatalogoOperacionDTO toDTO(CatalogoOperacion catalogo) {
        if (catalogo == null) {
            return null;
        }
        
        CatalogoOperacionDTO dto = new CatalogoOperacionDTO();
        
        if (catalogo.getId() != null) {
            dto.setClaveGrupoEmpresa(catalogo.getId().getClaveGrupoEmpresa());
            dto.setClaveEmpresa(catalogo.getId().getClaveEmpresa());
            dto.setClaveOperacion(catalogo.getId().getClaveOperacion());
        }
        
        dto.setTextoDescripcion(catalogo.getDescripcion());
        dto.setClaveAfectaSaldo(catalogo.getClaveAfectaSaldo());
        dto.setEstatusOperacion(catalogo.getEstatus());
        dto.setTextoObservaciones(catalogo.getObservaciones());
        
        return dto;
    }
    
    public List<CatalogoOperacionDTO> toCatalogoDTOList(List<CatalogoOperacion> catalogos) {
        if (catalogos == null) {
            return null;
        }
        return catalogos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}