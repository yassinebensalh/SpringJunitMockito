import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { environment } from 'src/environments/environment';
import { HttpClientModule } from '@angular/common/http';
import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
// Auth0
import { AuthModule } from '@auth0/auth0-angular';
// CUSTOM MODULES
import { NgZorroModule } from './ng-zorro.module';
// CUSTOM IMPORTS
import { AuthenticationAlertComponent } from './views/authentication-alert/authentication-alert.component';
import { NotFoundComponent } from './views/not-found/not-found.component';
import { LoadingComponent } from './components/loading/loading.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';

registerLocaleData(en);

@NgModule({
    declarations: [
        AppComponent,
        AuthenticationAlertComponent,
        NotFoundComponent,
        LoadingComponent,
        SidebarComponent,
        NavbarComponent,
        UserProfileComponent,
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        HttpClientModule,
        NgZorroModule,
    ],
    providers: [],
    bootstrap: [AppComponent],
})
export class AppModule {}
