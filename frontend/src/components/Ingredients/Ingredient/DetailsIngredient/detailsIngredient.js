/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
import React, {useState, useEffect} from 'react'
import axios from '../../../../custom-axios/axios'
import {useParams} from "react-router";
import Pizza from "../../../Pizzas/Pizza/pizza";

const DetailsIngredient = (props) => {
    let [pizzas, setPizzas] = useState();
    let [ingredient, setIngredient] = useState();
    const {id} = useParams();

    useEffect(() => {
        axios.get("/ingredients/" + id + "/pizzas").then((data) => {
            const pizzas = data.data.map((pizza, index) => {
                return (
                    <Pizza pizza={pizza} key={index} colClass={"col-md-6 mt-2 col-sm-12"}/>
                );
            });
            setPizzas(pizzas);
        });

        axios.get("/ingredients/" + id).then((data) => {
            setIngredient(data.data.name);
        });
    }, []);

    return (
        <div>
            <h1>Pizzas with {ingredient}</h1>
            <hr/>
            <div className={"row"}>
                {pizzas}
            </div>
        </div>
    )
};

export default DetailsIngredient;