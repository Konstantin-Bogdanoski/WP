/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
import React from "react";
import Pizza from "./Pizza/pizza.js"
import {Link} from "react-router-dom";

const Pizzas = (props) => {
    const pizzas = props.pizzas.map((pizza, index) => {
        return (
            <Pizza onDelete={props.onDelete} pizza={pizza} key={index} colClass={"col-md-6 mt-2 col-sm-12"}/>
        );
    });
    return (
        <div className={"row"}>
            <h4 className="text-upper text-center">Pizzas</h4>
            <div className={"row"}>
                {pizzas}
            </div>
            <Link className="btn btn-outline-secondary" to={"/pizzas/new"}>
                <span><strong>Add new pizza</strong></span>
            </Link>
        </div>
    )
};
export default Pizzas;