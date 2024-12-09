package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Colaboraciones.Colaboracion;
import com.example.demo.models.Colaboraciones.DonacionViandas.DonacionVianda;
import com.example.demo.models.Persona.Persona;
import com.example.demo.models.Rol.Colaborador;
import com.example.demo.repositories.ColaboradorRepository;
import com.example.demo.repositories.ColaboracionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Service
public class ReconocimientosExtra {
    @Autowired
    private CalculadoraPuntosService calculadoraPuntosService;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private ColaboracionRepository colaboracionRepository;

    public List<Persona> recomendarPersonas(double puntosReq, double viandasDonadasReq, int cantMaxColabs) {
        System.out.println("hola");
        List<Persona> personasRecomendadas = new ArrayList<>();

        // Obtener la lista de todos los colaboradores desde la base de datos
        List<Colaborador> colaboradores = colaboradorRepository.findColaboradoresWithPersonasHumanas();
        for (Colaborador colaborador : colaboradores) {
            System.out.println("Colaborador con uuid: " + colaborador.getUUID());
        }
        // Filtrar los colaboradores que cumplen con los requisitos
        for (Colaborador colaborador : colaboradores) {
            if (colaborador.getUUID() != null) {
                System.out.println("Colaborador con uuid: " + colaborador.getUUID());
                double puntosColaborador = calculadoraPuntosService.calcularPuntos(colaborador.getUUID());

                int viandasDonadasUltimoMes = getViandasDonadas(colaborador, 30);

                if (puntosColaborador >= puntosReq && viandasDonadasUltimoMes >= viandasDonadasReq) {

                    Persona persona = colaborador.getPersona();
                    System.out.println("Persona con nombre: " + persona.getNombre());
                    if (persona != null) {
                        personasRecomendadas.add(persona);
                    }
                }
                if (personasRecomendadas.size() >= cantMaxColabs) {
                    break;
                }
            }
        }

        return personasRecomendadas;
    }

    public int getViandasDonadas(Colaborador colaborador, int dias) {
        int viandasDonadas = 0;
        LocalDate fechaInicio = LocalDate.now().minusDays(dias); // Define el inicio del rango de tiempo

        List<Colaboracion> colaboraciones = colaboracionRepository.findByColaborador(colaborador);
        for (Colaboracion colaboracion : colaboraciones) {
            if (colaboracion instanceof DonacionVianda) {
                DonacionVianda donacion = (DonacionVianda) colaboracion;
                if (donacion.getFecha().isAfter(fechaInicio)) {
                    viandasDonadas++;
                }
            }
        }
        return viandasDonadas;
    }
}
