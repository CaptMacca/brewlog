import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { ConfirmationService } from 'primeng/components/common/confirmationservice';
import { MessageService } from 'primeng/components/common/messageservice';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { MenuModule } from 'primeng/menu';
import { MenubarModule } from 'primeng/menubar';
import { MessageModule } from 'primeng/message';
import { MessagesModule } from 'primeng/messages';
import { PanelModule } from 'primeng/panel';
import { PasswordModule } from 'primeng/password';
import { SidebarModule } from 'primeng/sidebar';
import { TableModule } from 'primeng/table';
import { TabViewModule } from 'primeng/tabview';
import { ToastModule } from 'primeng/toast';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    TableModule,
    MenuModule,
    MenubarModule,
    MessagesModule,
    MessageModule,
    ButtonModule,
    ConfirmDialogModule,
    ToastModule,
    PasswordModule,
    PanelModule,
    CardModule,
    TabViewModule,
    InputTextareaModule,
    InputTextModule,
    SidebarModule,
  ],
  exports: [
    TableModule,
    MenuModule,
    MenubarModule,
    MessagesModule,
    MessageModule,
    ButtonModule,
    ConfirmDialogModule,
    ToastModule,
    PasswordModule,
    PanelModule,
    CardModule,
    TabViewModule,
    InputTextareaModule,
    InputTextModule,
    SidebarModule,
  ],
  providers: [
    ConfirmationService,
    MessageService
  ]
})
export class PrimeNgModule { }
