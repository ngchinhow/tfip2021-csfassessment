import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Recipe } from 'src/app/models/models';
import { ServerService } from 'src/app/services/server.service';

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css']
})
export class RecipeListComponent implements OnInit {
  recipes: Partial<Recipe>[] = [];

  constructor(
    private sSvc: ServerService,
    private router: Router
  ) { }

  async ngOnInit(): Promise<void> {
    this.recipes = await this.sSvc.getAllRecipes();
  }

  showDetails(recipe: Partial<Recipe>) {
    this.router.navigate(['/recipe', recipe.id]);
  }

}
