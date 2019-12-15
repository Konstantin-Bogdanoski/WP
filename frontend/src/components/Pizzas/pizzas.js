/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
import React from "react";
import Pizza from "./Pizza/pizza.js"

const Pizzas = (props) => {
    const pizzas = props.pizzas.map((pizza, index) => {
        return (
            <Pizza pizza={pizza} key={index} colClass={"col-md-6 mt-2 col-sm-12"}/>
        );
    });
    return (
        <div className={"row"}>
            {pizzas}
        </div>
    )
};
export default Pizzas;