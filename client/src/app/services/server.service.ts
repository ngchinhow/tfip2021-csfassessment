import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { lastValueFrom } from "rxjs";
import { environment } from "src/environments/environment";
import { Recipe } from "../models/models";

const BASE_URI = environment.apiURI;

@Injectable()
export class ServerService {
  constructor(private http: HttpClient) { }

  getRecipe(id: string) {
    const headers = {
      'Accept': 'application/json'
    }
    return lastValueFrom(
      this.http.get<Recipe>(`${BASE_URI}/recipe/${id}`, { headers: headers })
    );
  }

  getAllRecipes() {
    const headers = {
      'Accept': 'application/json'
    }
    return lastValueFrom(
      this.http.get<Partial<Recipe>[]>(`${BASE_URI}/recipes`, { headers: headers })
    );
  }

  addRecipe(recipe: Partial<Recipe>): Promise<Recipe> {
    const headers = {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    }
    return lastValueFrom(
      this.http.post<any>(`${BASE_URI}/recipe`, recipe, { headers: headers })
    );
  }
}
