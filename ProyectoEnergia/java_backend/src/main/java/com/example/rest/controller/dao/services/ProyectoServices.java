package com.example.rest.controller.dao.services;

import com.example.rest.controller.dao.ProyectoDao;
import com.example.rest.controller.tda.list.LinkedList;
import com.example.rest.models.Proyecto;

import java.util.List;

public class ProyectoServices {
    private ProyectoDao proyectoDao;

    public ProyectoServices() {
        this.proyectoDao = new ProyectoDao();
    }

    public Boolean save() throws Exception {
        return proyectoDao.save();
    }

    public Boolean update() throws Exception {
        return proyectoDao.update();
    }

    public LinkedList<Proyecto> listAll() {
        return proyectoDao.getListAll();
    }

    public Proyecto getProyecto() {
        return proyectoDao.getProyecto();
    }

    public void setProyecto(Proyecto proyecto) {
        proyectoDao.setProyecto(proyecto);
    }

    // Método para obtener un proyecto por su id
    public Proyecto get(int id) {
        return proyectoDao.get(id);
    }

    public Boolean delete(Integer id) {
        return proyectoDao.delete(id);
    }

    // Método para obtener todos los proyectos
    public List<Proyecto> getAllProyectos() {
        return proyectoDao.getListAll().toList();
    }

    // Método para ordenar proyectos utilizando QuickSort
    public void quickSort(List<Proyecto> proyectos, String criterio, boolean ascendente) {
        proyectoDao.quickSort(proyectos, criterio, ascendente);
    }

    // Método para ordenar proyectos utilizando MergeSort
    public void mergeSort(List<Proyecto> proyectos, String criterio, boolean ascendente) {
        proyectoDao.mergeSort(proyectos, criterio, ascendente);
    }

    // Método para ordenar proyectos utilizando ShellSort
    public void shellSort(List<Proyecto> proyectos, String criterio, boolean ascendente) {
        proyectoDao.shellSort(proyectos, criterio, ascendente);
    }
}