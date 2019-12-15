/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
import React, {Component} from 'react';
import './App.css';
import Header from "../Header/header";
import {BrowserRouter as Router, Redirect, Route} from 'react-router-dom'
import Pizzas from "../Pizzas/pizzas";
import Ingredients from "../Ingredients/ingredients";
import Footer from "../Footer/footer";
import PizzaService from "../../service/pizzaService";
import IngredientService from "../../service/ingredientService";
import AddIngredient from "../Ingredients/addIngredient/addIngredient";
import EditIngredient from "../Ingredients/Ingredient/editIngredient/editIngredient";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            pizzas: [],
            ingredients: []
        }
    }

    componentDidMount() {
        this.loadIngredients();
        this.loadPizzas();
    }

    loadPizzas() {
        PizzaService.fetchPizzas().then(resp => {
            this.setState((prevState) => {
                return {
                    "pizzas": resp.data.content
                }
            });
        });
    }

    loadIngredients() {
        IngredientService.fetchIngredients().then(resp => {
            this.setState((prevState) => {
                return {
                    "ingredients": resp.data.content
                }
            })
        });
    }

    render() {
        return (
            <div className="App">
                <Router>
                    <Header/>
                    <main role="main" className="mt-3">
                        <div className="container">
                            <Route path={"/"} exact render={() => <Pizzas pizzas={this.state.pizzas}/>}>
                            </Route>
                            <Route path={"/pizzas"} render={() => <Pizzas pizzas={this.state.pizzas}/>}>
                            </Route>
                            <Route path="/ingredients" exact
                                   render={() => <Ingredients ingredients={this.state.ingredients}/>}>
                            </Route>
                            <Route path="/ingredients/new" exact render={() => <AddIngredient/>}>
                            </Route>
                            <Route path="/ingredients/:id/edit" exact
                                   render={() => <EditIngredient onSubmit={this.updateIngredient}/>}>
                            </Route>
                            <Redirect to={"/"}/>
                        </div>
                    </main>
                    <Footer/>
                </Router>
            </div>
        );
    }

    updateIngredient = ((updatedIngredient) => {
        IngredientService.saveOld(updatedIngredient).then(resp => {
            const newIngredient = resp.data;
            this.setState((prevState) => {
                const newIngrRef = prevState.ingredients.filter((item) => {
                    if (item.id === newIngredient.id) {
                        return newIngredient;
                    }
                    return item;
                });
                return {
                    "ingredients": newIngrRef,
                }
            });
        });
    });
}

export default App;