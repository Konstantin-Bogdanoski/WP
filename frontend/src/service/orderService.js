/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
import React, {Component} from "react";
import axios from '../custom-axios/axios.js';

const OrderService = {
    fetchOrdersPizza: () => {
        return axios.get("/orders/pizzas");
    }
};

export default OrderService;