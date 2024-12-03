package com.example.rest.controller.dao.services;

import com.example.rest.controller.dao.ProyectoDao;
import com.example.rest.controller.tda.list.LinkedList;
import com.example.rest.models.Proyecto;

public class ProyectoServices {
    private ProyectoDao obj;

    public ProyectoServices() {
        obj = new ProyectoDao();
    }

    public Boolean save() throws Exception {
        return obj.save();
    }

    public Boolean update() throws Exception {
        return obj.update();
    }

    public LinkedList listAll() {
        return obj.getListAll();
    }

    public Proyecto getProyecto() {
        return obj.getProyecto();
    }

    public void setProyecto(Proyecto proyecto) {
        obj.setProyecto(proyecto);
    }

    // Método para obtener un proyecto por su id
    public Proyecto get(int id) {
        return obj.get(id);
    }

    public LinkedList order(Integer type_order, String atributo) {
        return obj.order(type_order, atributo);
    }

    public Boolean delete(Integer id) {
        return obj.delete(id);
    }
    
}
