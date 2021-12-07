/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usa.divinacomedia.service;

import com.usa.divinacomedia.model.User;
import com.usa.divinacomedia.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * capa de servicios: parte logica del negocio , validaciones 
 * @author drida
 */
@Service
public class UserService {
    
    /**
     * 
     */
    @Autowired
    private UserRepository repository;
    

     /**
     * Obtener todos los registros
     * @return
     */
    public List<User> getAll(){
        return repository.getAll();
    }
    
    /**
     * 
     * @param id
     * @return
     */
    public Optional<User> getUserById (Integer id){
        return repository.getUserById(id);
    }


    /**
     *
     * @param user
     * @return
     */
    public User save(User user){
                Optional<User> userIdMaximo = repository.lastUserId();

if (user.getId() == null) {
            //valida el maximo id generado, si no hay ninguno aun el primer id sera 1
            if (userIdMaximo.isEmpty())
                user.setId(1);
            //si retorna informacion suma 1 al maximo id existente y lo asigna como el codigo del usuario
            else
                user.setId(userIdMaximo.get().getId() + 1);
        }
        
        Optional<User> e = repository.getUserById(user.getId());
        if (e.isEmpty()) {
            if (getUserByEmail(user.getEmail())==false){
                return repository.save(user);
            }else{
                return user;
            }
        }else{
            return user;
        }
        
    }


    /**
     * Obtener registro por email
     * @param email
     * @return
     */
    public boolean getUserByEmail(String email){
        return repository.getUserByEmail(email).isPresent();
    }

    /**
     * Obtener registro por email y password
     * @param email
     * @param password
     * @return
     */
    public User getUserByEmailAndPassword(String email, String password){
        Optional<User> user=repository.getUserByEmailAndPassword(email, password);
        if(user.isPresent()){
            return  user.get();
        }
        else{
            return new User();
        }
    }
    
    /**
     *
     * @param user
     * @return
     */
    public User update(User user){
        Optional<User> existsUser = repository.getUserById(user.getId());
        if(existsUser.isPresent()){
            if (user.getIdentification()!=null){
                existsUser.get().setIdentification(user.getIdentification());
            }
            if (user.getName()!=null){
                existsUser.get().setName(user.getName());
            }
           if (user.getBirthtDay()!=null){
                existsUser.get().setBirthtDay(user.getBirthtDay());
           }
            if (user.getMonthBirthtDay()!=null){
                existsUser.get().setMonthBirthtDay(user.getMonthBirthtDay());
            }
            if (user.getAddress()!=null){
                existsUser.get().setAddress(user.getAddress());
            }
            if (user.getCellPhone()!=null){
                existsUser.get().setCellPhone(user.getCellPhone());
            }
            if (user.getEmail()!=null){
                existsUser.get().setEmail(user.getEmail());
            }
            if (user.getPassword()!=null){
                existsUser.get().setPassword(user.getPassword());
            }
            if (user.getZone()!=null){
                existsUser.get().setZone(user.getZone());
            }
            if (user.getType()!=null){
                existsUser.get().setType(user.getType());
            }
            return repository.save(existsUser.get());
        }else {
            return user;
        }
    }
    
    /**
     * 
     * @param id
     * @return
     */
    public boolean delete(Integer id){
        Boolean aBoolean = getUserById(id).map(user -> {
            repository.delete(user.getId());
            return true;
        }).orElse(false);
        return aBoolean;
    }
    
    /**
     *
     * @param id
     * @return
     */
    public User getUserByIdUser(Integer id){
        return getUserById(id).orElse(new User());
    }


}