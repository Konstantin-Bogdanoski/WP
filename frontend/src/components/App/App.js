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
import AddIngredient from "../Ingredients/AddIngredient/addIngredient";
import EditIngredient from "../Ingredients/Ingredient/EditIngredient/editIngredient";
import DetailsIngredient from "../Ingredients/Ingredient/DetailsIngredient/detailsIngredient";

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

    updateIngredient = ((updatedIngredient) => {
        IngredientService.editIngredient(updatedIngredient).then(resp => {
            const newIngredient = resp.data;
            this.setState((prevState) => {
                const newIngrRef = prevState.ingredients.map(ingr => {
                    if (ingr.id === newIngredient.id) {
                        return newIngredient;
                    }
                    return ingr;
                });
                return {
                    "ingredients": newIngrRef
                }
            });
        });
        return <Redirect to={"/ingredients"}/>
    });

    saveIngredient = ((newIngredient) => {
        IngredientService.addIngredient(newIngredient).then(resp => {
            const newIngr = resp.data;
            this.setState((prevState) => {
                const newIngredients = prevState.ingredients.map((item) => {
                    return item;
                });
                newIngredients.push(newIngr);
                return {
                    "ingredients": newIngredients
                }
            });
        });
    });

    deleteIngredient = ((id) => {
        IngredientService.deleteIngredient(id).then(resp => {
            //TODO: AXIOS call to remove from DB
            this.setState((prevState) => {
                const newIngredients = prevState.ingredients.filter((ingredient, index) => {
                    return index !== id;
                });
                debugger;
                return {"ingredients": newIngredients}
            })
        });
    });

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
                                   render={() => <Ingredients ingredients={this.state.ingredients}
                                                              onDelete={this.deleteIngredient}/>}>
                            </Route>
                            <Route path="/ingredients/new" exact
                                   render={() => <AddIngredient onSubmit={this.saveIngredient}/>}>
                            </Route>
                            <Route path="/ingredients/:id/edit" exact
                                   render={() => <EditIngredient onSubmit={this.updateIngredient}/>}>
                            </Route>
                            <Route path="/ingredients/:id/details" exact
                                   render={() => <DetailsIngredient/>}>
                            </Route>
                            <Redirect to={"/"}/>
                        </div>
                    </main>
                    <Footer/>
                </Router>
            </div>
        );
    }
}

export default App;