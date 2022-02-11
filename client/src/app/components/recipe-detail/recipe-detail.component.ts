import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Recipe } from 'src/app/models/models';
import { ServerService } from 'src/app/services/server.service';

@Component({
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.css']
})
export class RecipeDetailComponent implements OnInit {
  recipe!: Recipe;

  constructor(
    private sSvc: ServerService,
    private activatedRoute: ActivatedRoute
  ) { }

  async ngOnInit(): Promise<void> {
    const recipeId = this.activatedRoute.snapshot.params['recipeId'];
    this.recipe = await this.sSvc.getRecipe(recipeId);
  }

}
