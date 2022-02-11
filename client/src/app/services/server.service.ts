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
    return lastValueFrom(
      this.http.get<Recipe>(`${BASE_URI}/recipe/${id}`)
    );
  }

  getAllRecipes() {
    return lastValueFrom(
      this.http.get<Partial<Recipe>[]>(`${BASE_URI}/recipes`)
    );
  }

  addRecipe(recipe: Partial<Recipe>): Promise<Recipe> {
    const headers = {
      'Accept': 'application/json'
    }
    return lastValueFrom(
      this.http.post<any>(`${BASE_URI}/recipe`, recipe, { headers: headers })
    );
  }
}
