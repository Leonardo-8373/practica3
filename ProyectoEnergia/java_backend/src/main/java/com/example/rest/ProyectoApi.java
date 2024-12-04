package com.example.rest;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.example.rest.controller.dao.services.ProyectoServices;
import com.example.rest.controller.tda.list.LinkedList;
import com.example.rest.models.Proyecto;

import javax.ws.rs.PathParam;

@Path("proyecto")
public class ProyectoApi {

    private ProyectoServices proyectoDao = new ProyectoServices();

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProyectos() {
        HashMap<String, Object> map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        map.put("msg", "OK");
        map.put("data", ps.listAll().toArray());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[]{});
        }
        return Response.ok(map).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProyecto(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        try {
            ps.setProyecto(ps.get(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("msg", "OK");
        map.put("data", ps.getProyecto());
        if (ps.getProyecto() == null) {
            map.put("data", "No existe el proyecto con ese identificador");
            return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
        }
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveProyecto(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            ProyectoServices ps = new ProyectoServices();
            
            ps.getProyecto().setNombre(map.get("nombre").toString());
            ps.getProyecto().setDescripcion(map.get("descripcion").toString());
            ps.getProyecto().setInversion(Double.parseDouble(map.get("inversion").toString())); // Corregido: "costo" -> "inversion"
            ps.getProyecto().setTiempoVida(Integer.parseInt(map.get("tiempoVida").toString()));
            ps.getProyecto().setFechaInicio(map.get("fechaInicio").toString());
            ps.getProyecto().setFechaFin(map.get("fechaFin").toString());
            ps.getProyecto().setInversionistas((String[]) map.get("inversionistas"));
            ps.getProyecto().setElectricidadGeneradaDiaria(Double.parseDouble(map.get("electricidadGeneradaDiaria").toString())); // Corregido: "generacionDiaria" -> "electricidadGeneradaDiaria"
            ps.save();
            res.put("msg", "OK");
            res.put("data", "Proyecto registrado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProyecto(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            ProyectoServices ps = new ProyectoServices();
            ps.setProyecto(ps.get(Integer.parseInt(map.get("id").toString())));
            ps.getProyecto().setNombre(map.get("nombre").toString());
            ps.getProyecto().setDescripcion(map.get("descripcion").toString());
            ps.getProyecto().setInversion(Double.parseDouble(map.get("inversion").toString())); // Corregido: "costo" -> "inversion"
            ps.getProyecto().setTiempoVida(Integer.parseInt(map.get("tiempoVida").toString()));
            ps.getProyecto().setFechaInicio(map.get("fechaInicio").toString());
            ps.getProyecto().setFechaFin(map.get("fechaFin").toString());
            ps.getProyecto().setInversionistas((String[]) map.get("inversionistas"));
            ps.getProyecto().setElectricidadGeneradaDiaria(Double.parseDouble(map.get("electricidadGeneradaDiaria").toString())); // Corregido: "generacionDiaria" -> "electricidadGeneradaDiaria"
            ps.update();
            res.put("msg", "OK");
            res.put("data", "Proyecto actualizado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProyecto(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            ProyectoServices ps = new ProyectoServices();
            ps.delete(id);
            map.put("msg", "OK");
            map.put("data", "Proyecto eliminado correctamente");
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "Error");
            map.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @GET
    @Path("/sort/{metodo}/{criterio}/{orden}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Proyecto> sortProyectos(@PathParam("metodo") String metodo,
                                        @PathParam("criterio") String criterio,
                                        @PathParam("orden") String orden) {
        boolean ascendente = orden.equalsIgnoreCase("asc");
        List<Proyecto> proyectos = proyectoDao.getAllProyectos(); // Obtener los proyectos
        
        // Selección del método de ordenación
        switch (metodo) {
            case "quick":
                if (criterio.equals("id")) {
                    proyectoDao.quickSort(proyectos, "id", ascendente);
                } else {
                    proyectoDao.quickSort(proyectos, "nombre", ascendente);
                }
                break;
            case "merge":
                if (criterio.equals("id")) {
                    proyectoDao.mergeSort(proyectos, "id", ascendente);
                } else {
                    proyectoDao.mergeSort(proyectos, "nombre", ascendente);
                }
                break;
            case "shell":
                if (criterio.equals("id")) {
                    proyectoDao.shellSort(proyectos, "id", ascendente);
                } else {
                    proyectoDao.shellSort(proyectos, "nombre", ascendente);
                }
                break;
            default:
                // Si no se especifica un método válido, por defecto usa "quick"
                if (criterio.equals("id")) {
                    proyectoDao.quickSort(proyectos, "id", ascendente);
                } else {
                    proyectoDao.quickSort(proyectos, "nombre", ascendente);
                }
                break;
        }
        return proyectos;
    }
}
