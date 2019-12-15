/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
import React, {Component} from "react";
import axios from '../custom-axios/axios.js';
import qs from 'qs';

const PizzaService = {
    fetchPizzas: () => {
        return axios.get("/pizzas");
    },

    fetchPizzasPaged: (page, pageSize) => {
        return axios.get("/pizzas", {
            headers: {
                'page': page, 'page-size': pageSize
            }
        })
    },

    /*addPizza: (pizza) => {
        const data = {
            ...pizza,
            pizzaName: pizza.pizza.name
        };
        const formParams = qs.stringify(data);
        return axios.post("/api/consultations", formParams, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            }
        });
    },*/
    /*updateConsultationTerm: (term) => {
        const data = {
            ...term,
            roomName: term.room.name
        }
        const slotId = term.slotId;
        const formParams = qs.stringify(data);
        return axios.patch("/api/consultations/" + slotId, formParams, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'professorId': 'kostadin.mishev'
            }
        });
    }*/

};

export default PizzaService;