/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
import React, {useEffect, useState} from 'react'
import {Link, withRouter} from 'react-router-dom';
import axios from "../../../custom-axios/axios";

const AddPizza = (props) => {
    let [ingredients, setIngredient] = useState();

    useEffect(() => {
        axios.get("/ingredients").then((data) => {
            const ingredients = Object.keys(data.data.content).map((ingredient, index) => {
                return (
                    <tr>
                        <td scope="col">
                            <label>{data.data.content[index].name}</label>
                        </td>
                        <td scope="col">
                            <input id={ingredient} onClick={handleCheckboxChange} key={index} type="checkbox"
                                   name={"newIngredients"}
                                   value={data.data.content[index].id}/>
                        </td>
                        <td scope="col">
                            <input disabled id={data.data.content[index].id + "amount"} key={index} type="number"
                                   min="0" max="500"
                                   name=""/> grams
                        </td>
                    </tr>
                );
            });
            setIngredient(ingredients);
        });
    }, []);

    const handleCheckboxChange = (e) => {
        const paramName = e.target.name;
        const paramValue = e.target.value;
        document.getElementById(e.target.value + "amount").disabled = !document.getElementById(e.target.value + "amount").disabled;
    };

    const onFormSubmit = (e) => {
        e.preventDefault();

        let final = [];
        let newIngredients = Object.keys(e.target.newIngredients).map((cbox) => {
            if (e.target.newIngredients[cbox].checked)
                return e.target.newIngredients[cbox];
        }).filter((item) => {
            if (item !== undefined)
                return item;
        }).map((item) => {
            let id = item.value;
            let amount = document.getElementById(id + "amount").value;
            let obj = {};
            obj[id] = amount;
            final.push(obj);
        });
        props.history.push('/pizzas');
        props.onSubmit(
            {
                "name": e.target.pizzaName.value,
                "description": e.target.pizzaDescription.value,
                "veggie": e.target.isVeggie.checked,
                "newIngredients": final
            }
        );
    };

    return (
        <div className="form-group">
            <form className="card" onSubmit={onFormSubmit}>
                <h4 className="text-upper text-left">Add Pizza</h4>
                <div className="form-group row">
                    <label htmlFor="pizzaName" className="col-sm-4 offset-sm-1 text-left">Name</label>
                    <div className="col-sm-6">
                        <input type="text"
                               className="form-control" id="pizzaName" name={"pizzaName"}
                               placeholder="Pizza name" required maxLength="50"/>
                    </div>
                </div>
                <div className="form-group row">
                    <label htmlFor="pizzaDescription" className="col-sm-4 offset-sm-1 text-left">Description</label>
                    <div className="col-sm-6">
                        <textarea
                            className="form-control" id="pizzaDescription" name={"pizzaDescription"}
                            placeholder="Pizza description" required maxLength="255"/>
                    </div>
                </div>
                <div className="form-group row">
                    <label htmlFor="veggie" className="col-sm-4 offset-sm-1 text-left">Veggie</label>
                    <div className="col-sm-6 col-xl-4">
                        <input type="checkbox"
                               className="form-control" id="veggie" name={"isVeggie"}/>
                    </div>
                </div>
                <div className="form-group">
                    <table className="table tr-history table-striped small">
                        <thead>
                        <tr>
                            <th scope="col">
                                Ingredient
                            </th>
                            <th scope="col">
                                Check
                            </th>
                            <th scope="col">
                                Amount
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        {ingredients}
                        </tbody>
                    </table>
                </div>
                <div className="form-group row">
                    <div
                        className="offset-sm-1 col-sm-3  text-center">
                        <button
                            type="submit"
                            className="btn btn-primary text-upper">
                            Save
                        </button>
                    </div>
                    <div
                        className="offset-sm-1 col-sm-3  text-center">
                        <button
                            type="reset"
                            className="btn btn-warning text-upper">
                            Reset
                        </button>
                    </div>
                    <div
                        className="offset-sm-1 col-sm-3  text-center">
                        <Link to={"/pizzas"}
                              className="btn btn-danger text-upper">
                            Cancel
                        </Link>
                    </div>
                </div>
            </form>
        </div>
    )
};

export default withRouter(AddPizza);