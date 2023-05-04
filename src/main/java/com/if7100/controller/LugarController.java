package com.if7100.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author Julio Jarquin
 * Fecha: 20 de abril del 2023
 */
import com.if7100.entity.Lugar;
import com.if7100.service.LugarService;
import com.if7100.service.TipoLugarService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LugarController {

    @Autowired
    private LugarService lugarService;
    private TipoLugarService tipoLugarService;

    public LugarController(LugarService lugarService, TipoLugarService tipoLugarService) {
        super();
        this.lugarService=lugarService;
        this.tipoLugarService= tipoLugarService;
    }

    //Mostrar todos lugares
    @GetMapping("/lugar/{Id}")
    public String listStudents(Model model, @PathVariable Integer Id, HttpSession session) {
        session.setAttribute("idLugarHecho", Id);
        List<Lugar> listaLugar=lugarService.getAllLugares(Id);
        model.addAttribute("lugar",listaLugar);
        return "lugares";
    }

    //Eliminar Lugar
    @GetMapping("/lugar/eliminar/{Id}")
    public String deleteLugar(@PathVariable Integer Id, HttpSession session) {
        //Integer IdDelHecho = lugarService.getLugarById(Id).getCIHecho();
        Integer idLugarHecho = (Integer) session.getAttribute("idLugarHecho");
        lugarService.deleteLugarById(Id);
        return "redirect:/lugar/"+ idLugarHecho;
    }

    //Editar Lugar
    @GetMapping("/lugar/edit/{Id}")
    public String editLugarForm(@PathVariable Integer Id, Model model) {
        model.addAttribute("lugar", lugarService.getLugarById(Id));
        model.addAttribute("tipoLugar", tipoLugarService.getAllTipoLugares());
        return "edit_lugar";
    }

    @PostMapping("/lugar/{Id}")
    public String updateLugar(@PathVariable Integer Id, @ModelAttribute("lugar") Lugar lugar) {
        Lugar existingLugar=lugarService.getLugarById(Id);
        existingLugar.setCI_Codigo(Id);
        existingLugar.setCIHecho(existingLugar.getCIHecho());
        existingLugar.setCV_Descripcion(lugar.getCV_Descripcion());
        existingLugar.setCI_Tipo_Lugar(lugar.getCI_Tipo_Lugar());
        existingLugar.setCV_Direccion(lugar.getCV_Direccion());
        existingLugar.setCV_Ciudad(lugar.getCV_Ciudad());
        existingLugar.setCI_Pais(lugar.getCI_Pais());
        lugarService.updateLugar(existingLugar);
        return "redirect:/lugar/"+ existingLugar.getCIHecho();
    }





    //Metodo Prueba, nose si esta bueno
//    @GetMapping("/lugar/new/{Id}")
//    public String createLugarForm(Model model, @PathVariable Integer Id, HttpSession session) {
//        Lugar lugar = new Lugar();
//        int ruta;
//
//        Integer idLugarHecho = (Integer) session.getAttribute("idLugarHecho");
//        System.out.println(session.getAttribute("idLugarHecho"));
//        System.out.println(idLugarHecho);
//        if(idLugarHecho != null && idLugarHecho >= 1) {
//            ruta=idLugarHecho;
//            lugar.setCIHecho(idLugarHecho);
//        }
//        else {
//            ruta=Id;
//            lugar.setCIHecho(Id);
//        }
//        model.addAttribute("ruta",Id);
//        model.addAttribute("lugar", lugar);
//        model.addAttribute("tipoLugar", tipoLugarService.getAllTipoLugares());
//        return "create_lugar";
//    }




    @PostMapping("/lugar")
    public String saveLugar(@ModelAttribute("lugar") Lugar lugar) {
        lugarService.saveLugar(lugar);
        return "redirect:/lugar/"+lugar.getCIHecho();
    }


    // ESTE METODO ESTA BUENO PERO SOLO CUANDO ENTRA POR VER LUGARES// ESTE SI ESTA PROBADO
    //Nuevo Lugar
    @GetMapping("/lugar/new/{Id}")
		public String createLugarForm(Model model, @PathVariable Integer Id) {
			Lugar lugar = new Lugar();
            lugar.setCIHecho(Id);
			model.addAttribute("lugar", lugar);
			model.addAttribute("tipoLugar", tipoLugarService.getAllTipoLugares());
			return "create_lugar";
		}

//	@PostMapping("/lugar")
//	public String saveLugar(@ModelAttribute("lugar") Lugar lugar) {
//		lugarService.saveLugar(lugar);
//		return "redirect:/lugar/"+lugar.getCIHecho();
//	}

    @GetMapping("/lugares")
    public String listLugares(Model model) {
        List<Lugar> listaLugar=lugarService.getAllLugar();
        model.addAttribute("lugar",listaLugar);
        return "lugares";
    }

    @GetMapping("/lugar")
    public String listLugares(@RequestParam("id") Integer id,  Model model) {
        List<Lugar> listaLugar = lugarService.getAllLugares(id);
        model.addAttribute("lugar",listaLugar);
        return "lugares";
    }


    //Este Metodo esta bueno pero solo sirve si selecciona ver la lista de lugares de un hecho y desde la misma ventana de lugar se agrega un lugar de un hecho en especifico

//Nuevo Lugar
//    @GetMapping("/lugares/new")
//    public String createHechoForm(Model model){
//        Lugar lugar = new Lugar();
//        model.addAttribute("lugar", lugar);
//        model.addAttribute("tipoLugar", tipoLugarService.getAllTipoLugares());
//        return "create_lugar";
//    }


}
