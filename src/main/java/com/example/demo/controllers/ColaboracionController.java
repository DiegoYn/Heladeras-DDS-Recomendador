package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.example.demo.services.ReconocimientosExtra;
import com.example.demo.models.Persona.Persona;

@RestController
@RequestMapping("/service-2")
@CrossOrigin(origins = "https://heladeras-dds.vercel.app/")
public class ColaboracionController {

    @Autowired
    private ReconocimientosExtra reconocimientosExtra;

    @GetMapping("/recomendaciones-colaboradores")
    public ResponseEntity<List<Persona>> recomendarColaboradores(@RequestParam double puntosReq,
            @RequestParam double viandasDonadasReq, @RequestParam int cantMaxColabs) {
        try {
            List<Persona> personas = reconocimientosExtra.recomendarPersonas(puntosReq, viandasDonadasReq,
                    cantMaxColabs);
            return ResponseEntity.ok(personas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}