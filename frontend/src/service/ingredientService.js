/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
import React, {Component} from "react";
import axios from '../custom-axios/axios.js';
import qs from 'qs';

const IngredientService = {
    fetchIngredients: () => {
        return axios.get("/ingredients");
    },

    fetchIngredientsPaged: (page, pageSize) => {
        return axios.get("/ingredients", {
            headers: {
                'page': page, 'page-size': pageSize
            }
        })
    },

    deleteIngredient(ingredientID) {
        return axios.delete("/ingredients/" + ingredientID)
    },

    detailsIngredient(ingredientID) {
        return axios.get("/ingredients/" + ingredientID + "/pizzas")
    },

    editIngredient(updatedIngredient) {
        const data = {
            ...updatedIngredient,
            name: updatedIngredient.name
        };
        const ingredientId = updatedIngredient.ingredientID;
        const formParams = qs.stringify(data);
        return axios.patch("/ingredients/" + ingredientId, formParams, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });
    },

    addIngredient(ingredient) {
        const data = {
            ...ingredient,
            ingredientName: ingredient.name
        };
        const formParams = qs.stringify(data);
        return axios.post("/ingredients", formParams, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            }
        });
    },
};

export default IngredientService;