import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Recipe } from 'src/app/models/models';
import { ServerService } from 'src/app/services/server.service';

@Component({
  selector: 'app-recipe-add',
  templateUrl: './recipe-add.component.html',
  styleUrls: ['./recipe-add.component.css']
})
export class RecipeAddComponent implements OnInit {
  form!: FormGroup;
  ingredientsArray!: FormArray;


  constructor(
    private fb: FormBuilder,
    private sSvc: ServerService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.ingredientsArray = this.fb.array(
      [],
      [Validators.required]
    );
    this.form = this.fb.group({
      title: this.fb.control(
        '',
        [Validators.minLength(3), Validators.required]
      ),
      image: this.fb.control(
        '',
        [Validators.required]
      ),
      instruction: this.fb.control(
        '',
        [Validators.minLength(3), Validators.required]
      ),
      ingredients: this.ingredientsArray
    });
  }

  private createIngredientControl(): FormControl {
    return this.fb.control(
      '',
      [Validators.minLength(3), Validators.required]
    );
  }

  castToFormControl(ac: AbstractControl): FormControl {
    return ac as FormControl;
  }

  addIngredient() {
    this.ingredientsArray.push(this.createIngredientControl());
  }

  async addRecipe() {
    const recipe = this.form.value as Partial<Recipe>;
    console.info(recipe);
    await this.sSvc.addRecipe(recipe);
    this.router.navigate(['/']);
  }

  removeIngredient(index: number) {
    this.ingredientsArray.removeAt(index);
  }
}
