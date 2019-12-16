/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
import React, {useState, useEffect, Component} from 'react'
import axios from '../../../../custom-axios/axios'
import {useParams} from "react-router";
import {Link} from "react-router-dom";
import Pizza from "../../../Pizzas/Pizza/pizza";

const DetailsIngredient = (props) => {
    let [pizzas, setPizzas] = useState();
    const {id} = useParams();

    useEffect(() => {
        axios.get("/ingredients/" + id + "/pizzas").then((data) => {
            const pizzas = data.data.map((pizza, index) => {
                return (
                    <Pizza pizza={pizza} key={index} colClass={"col-md-6 mt-2 col-sm-12"}/>
                );
            });
            setPizzas(pizzas);
        })
    }, []);

    return (
        <div className={"row"}>
            {pizzas}
        </div>
    )
};

export default DetailsIngredient;