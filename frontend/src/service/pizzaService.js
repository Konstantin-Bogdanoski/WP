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

    addPizza: (pizza) => {
        const data = {
            ...pizza,
        };
        const formParams = qs.stringify(data);
        debugger;
        //return axios.post("/pizzas", formParams, {
        //    headers: {
        //        'Content-Type': 'application/x-www-form-urlencoded',
        //    }
        //});
    },
};

export default PizzaService;