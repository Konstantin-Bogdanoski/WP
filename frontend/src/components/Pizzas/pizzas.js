/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
import React from "react";
import Pizza from "./Pizza/pizza.js"
import {Link} from "react-router-dom";
import SearchForm from "./SearchForm/searchForm";

const Pizzas = (props) => {
    const pizzas = props.pizzas.map((pizza, index) => {
        return (
            <Pizza onDelete={props.onDelete} pizza={pizza} key={pizza.id} colClass={"col-md-6 mt-2 col-sm-12"}/>
        );
    });

    return (
        <div className={"row"}>
            <header>
                <h4 className="text-upper text-center">Pizzas</h4>
                <SearchForm onSearch={props.onSearch}/>
            </header>
            <main>
                <div className={"row"}>
                    {pizzas}
                </div>
                <Link className="btn btn-outline-secondary" to={"/pizzas/new"}>
                    <span><strong>Add new pizza</strong></span>
                </Link>
            </main>
        </div>
    )
};
export default Pizzas;