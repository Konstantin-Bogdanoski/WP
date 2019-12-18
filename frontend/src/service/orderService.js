/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
import React, {Component} from "react";
import axios from '../custom-axios/axios.js';

const OrderService = {
    fetchOrdersPizza: () => {
        return axios.get("/orders/pizzas");
    },
    fetchFavoriteHours: () => {
        return axios.get("/orders/hours");
    }
};

export default OrderService;