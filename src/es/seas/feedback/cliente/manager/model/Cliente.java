/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.seas.feedback.cliente.manager.model;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Ricardo
 */
@Entity
@Table(name="Cliente")
public class Cliente extends Persona {
    public static void main(String[] args){
    Thread t = new Thread(){
        public void run(){
            System.out.println("Running Thread One");
            Thread h = new Thread(){
            public void run(){
                System.out.println("Running Thread Two");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            };
            h.start();
        }
    };
    t.start();
    }
    
}