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
import FavoritePizza from "../Statistic/FavoritePizza/favoritePizza";
import OrderService from "../../service/orderService";
import AddPizza from "../Pizzas/AddPizza/addPizza";
import FavoriteHours from "../Statistic/FavoriteHours/favoriteHours";
import Pizza from "../Pizzas/Pizza/pizza";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            pizzas: [],
            ingredients: [],
            orders: [],
            hours: []
        }
    }

    componentDidMount() {
        this.loadIngredients();
        this.loadPizzas();
        this.loadOrdersPizza();
        this.loadFavoriteHours();
    }

    loadPizzas() {
        PizzaService.fetchPizzas().then(resp => {
            this.setState(() => {
                return {
                    "pizzas": resp.data.content
                }
            });
        });
    }

    loadIngredients() {
        IngredientService.fetchIngredients().then(resp => {
            this.setState(() => {
                return {
                    "ingredients": resp.data.content
                }
            })
        });
    }

    loadOrdersPizza() {
        OrderService.fetchOrdersPizza().then(resp => {
            this.setState(() => {
                return {
                    "orders": resp.data
                }
            })
        })
    }

    loadFavoriteHours() {
        OrderService.fetchFavoriteHours().then(resp => {
            this.setState(() => {
                return {
                    "hours": resp.data
                }
            })
        })
    }

    searchPizzas = ((searchTerm) => {
        PizzaService.findPizzas(searchTerm).then(resp => {
            this.setState(() => {
                return {
                    "pizzas": resp.data.content
                }
            })
        })
    });

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

    savePizza = ((newPizza) => {
        PizzaService.addPizza(newPizza).then(resp => {
            const newPiz = resp.data;
            this.setState((prevState) => {
                const newPizzas = prevState.pizzas.map((item) => {
                    return item;
                });
                newPizzas.push(newPiz);
                return {
                    "pizzas": newPizzas
                }
            });
        });
    });

    deleteIngredient = ((id) => {
        IngredientService.deleteIngredient(id).then();
        this.setState((prevState) => {
            const newIngredients = prevState.ingredients.filter((ingredient) => {
                return ingredient.id !== id;
            });
            return {"ingredients": newIngredients}
        });
        this.loadIngredients();
    });

    deletePizza = ((id) => {
        PizzaService.deletePizza(id).then((res) =>
            this.loadPizzas()
        );
    });

    render() {
        return (
            <div className="App">
                <Router>
                    <Header/>
                    <main role="main" className="mt-3">
                        <div className="container">
                            <Route path={"/"} exact render={() => <FavoritePizza orders={this.state.orders}/>}/>
                            <Route path={"/pizzas"} exact render={() => <Pizzas pizzas={this.state.pizzas}
                                                                                onDelete={this.deletePizza}
                                                                                onSearch={this.searchPizzas}/>}/>
                            <Route path="/ingredients" exact
                                   render={() => <Ingredients ingredients={this.state.ingredients}
                                                              onDelete={this.deleteIngredient}/>}/>
                            <Route path="/ingredients/new" exact
                                   render={() => <AddIngredient onSubmit={this.saveIngredient}/>}/>
                            <Route path="/ingredients/:id/edit" exact
                                   render={() => <EditIngredient onSubmit={this.updateIngredient}/>}/>
                            <Route path="/ingredients/:id/details" exact
                                   render={() => <DetailsIngredient/>}/>
                            <Route path="/pizzas/new" exact render={() => <AddPizza onSubmit={this.savePizza}/>}/>
                            <Redirect to={"/"}/>
                            <Route path={"/"} exact render={() => <FavoriteHours hours={this.state.hours}/>}/>
                        </div>
                    </main>
                    <Footer/>
                </Router>
            </div>
        );
    }
}

export default App;