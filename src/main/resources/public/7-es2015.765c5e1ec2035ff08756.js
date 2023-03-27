(window.webpackJsonp=window.webpackJsonp||[]).push([[7],{NSaA:function(t,e,a){"use strict";a.r(e),a.d(e,"PaymentModule",(function(){return z}));var i=a("ofXK"),n=a("tyNb"),o=a("3Pt+"),r=a("fXoL"),c=a("tk/3");let l=(()=>{class t{constructor(t){this.http=t}save(t){return this.http.post("transaction/payment",t)}findAll(){return this.http.get("transaction/payments")}findById(t){return this.http.get("transaction/payment/find",{params:{id:t}})}delete(t){return this.http.delete("transaction/payment/delete",{params:{id:t.toString()}})}deleteDetail(t,e){let a=new c.d;return a=a.append("id",t),a=a.append("detailId",e.toString()),this.http.delete("transaction/payment/detail/delete",{params:a})}findOrdersByClient(t,e){let a=new c.d;return a=a.append("clientId",t),a=a.append("paymentType",e),this.http.get("transaction/record/client",{params:a})}findByCLientId(t){let e=new c.d;return e=e.append("clientId",t),this.http.get("transaction/payment/client",{params:e})}findPaymentReport(t,e,a){let i=new c.d;return i=i.append("clientId",t),i=i.append("fromDate",e),i=i.append("toDate",a),this.http.get("transaction/payment/report",{params:i})}}return t.\u0275fac=function(e){return new(e||t)(r.bc(c.b))},t.\u0275prov=r.Nb({token:t,factory:t.\u0275fac,providedIn:"root"}),t})();var b=a("Deg8"),d=a("0IaG"),s=a("zYZu"),m=a("NFeN"),p=a("kmnG"),u=a("qFsG"),h=a("iadO"),f=a("d3UM"),y=a("FKr1");function g(t,e){if(1&t&&(r.Xb(0,"mat-option",28),r.Hc(1),r.Wb()),2&t){const t=e.$implicit;r.oc("value",t.clientId),r.Cb(1),r.Jc(" ",t.clientName," ")}}function v(t,e){1&t&&(r.Xb(0,"div",18),r.Xb(1,"h5"),r.Hc(2,"Total amount sent"),r.Wb(),r.Wb())}function W(t,e){1&t&&(r.Xb(0,"div",18),r.Xb(1,"h5"),r.Hc(2,"Total amout received"),r.Wb(),r.Wb())}function X(t,e){if(1&t&&(r.Xb(0,"div",2),r.Gc(1,v,3,0,"div",31),r.Gc(2,W,3,0,"div",31),r.Xb(3,"div",18),r.Xb(4,"b"),r.Hc(5),r.jc(6,"currency"),r.Wb(),r.Wb(),r.Wb()),2&t){const t=e.$implicit;r.Cb(1),r.oc("ngIf","SEND"===t.key),r.Cb(1),r.oc("ngIf","RECEIVE"===t.key),r.Cb(3),r.Ic(r.lc(6,3,t.value,"INR"))}}function I(t,e){if(1&t&&(r.Xb(0,"div",29),r.Xb(1,"div",3),r.Xb(2,"h4"),r.Xb(3,"b"),r.Hc(4),r.Wb(),r.Wb(),r.Wb(),r.Xb(5,"div",9),r.Gc(6,X,7,6,"div",30),r.jc(7,"keyvalue"),r.Wb(),r.Wb()),2&t){const t=e.$implicit;r.Cb(4),r.Ic(t.key),r.Cb(2),r.oc("ngForOf",r.kc(7,2,t.value))}}const C=function(){return["/payment/payment/edit"]},S=function(t){return{id:t}};function D(t,e){if(1&t&&(r.Xb(0,"tbody"),r.Xb(1,"tr"),r.Xb(2,"td"),r.Hc(3),r.Wb(),r.Xb(4,"td"),r.Hc(5),r.Wb(),r.Xb(6,"td"),r.Hc(7),r.jc(8,"currency"),r.Wb(),r.Xb(9,"td"),r.Hc(10),r.jc(11,"currency"),r.Wb(),r.Xb(12,"td"),r.Hc(13),r.jc(14,"currency"),r.Wb(),r.Xb(15,"td"),r.Hc(16),r.Wb(),r.Xb(17,"td"),r.Xb(18,"a",32),r.Xb(19,"mat-icon",33),r.Hc(20,"edit"),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Wb()),2&t){const t=e.$implicit;r.Cb(3),r.Ic(t.paymentId),r.Cb(2),r.Ic(t.paymentDateString),r.Cb(2),r.Ic(r.lc(8,8,t.projectPayment,"INR")),r.Cb(3),r.Ic(r.lc(11,11,t.otherPayment,"INR")),r.Cb(3),r.Ic(r.lc(14,14,t.totalPayment,"INR")),r.Cb(3),r.Ic(t.notes),r.Cb(2),r.oc("routerLink",r.qc(17,C))("queryParams",r.rc(18,S,t.paymentId))}}let w=(()=>{class t{constructor(t,e,a,i,n,o,r){this.payService=t,this.clientService=e,this.dialog=a,this.route=i,this.notificationService=n,this.fb=o,this.datePipe=r,this.payments=[],this.clients=[],this.paymentView=null,this.isSearched=!1}ngOnInit(){this.getClients(),this.payForm=this.fb.group({clientId:new o.c(""),fromDate:new o.c(""),fromDateString:new o.c(""),toDate:new o.c(null),toDateString:new o.c("")})}getClients(){this.clientService.findAll().subscribe(t=>{this.clients=t})}populatDetailLists(t){this.payService.findByCLientId(t.value).subscribe(t=>{this.payments=t})}onSubmit(){this.payForm.valid&&(this.fromDate=this.payForm.value.fromDate,this.toDate=this.payForm.value.toDate,this.clientId=this.payForm.value.clientId,null!=this.payForm.value.fromDate&&(this.fromDateString=this.datePipe.transform(this.fromDate,"dd/MM/yyyy")),null!=this.payForm.value.toDate&&(this.toDateString=this.datePipe.transform(this.toDate,"dd/MM/yyyy")),this.payService.findPaymentReport(this.clientId,this.fromDateString,this.toDateString).subscribe(t=>{this.summary=t,this.payments=this.summary.details,this.paymentView=this.summary.view}),console.log(this.paymentView),this.isSearched=!0)}}return t.\u0275fac=function(e){return new(e||t)(r.Rb(l),r.Rb(b.a),r.Rb(d.b),r.Rb(n.a),r.Rb(s.a),r.Rb(o.b),r.Rb(i.e))},t.\u0275cmp=r.Lb({type:t,selectors:[["app-pay-show"]],decls:59,vars:11,consts:[[1,"main-content"],[1,"container-fluid"],[1,"row"],[1,"col-md-12"],[1,"card","card-plain"],[1,"card-header","card-header-info"],[1,"card-title"],["routerLink","/payment/payment/add"],[1,"gridIcon"],[1,"card-body"],[3,"formGroup","ngSubmit"],["headerForm","ngForm"],[1,"col-md-2"],["matInput","","formControlName","fromDate",3,"matDatepicker"],["matSuffix","",3,"for"],["picker1",""],["matInput","","formControlName","toDate",3,"matDatepicker"],["picker2",""],[1,"col-md-3"],[1,"example-full-width"],["placeholder","Client","formControlName","clientId"],[3,"value",4,"ngFor","ngForOf"],["mat-raised-button","","type","submit",1,"btn","btn-success","pull-right",3,"disabled","click"],[1,"text-primary"],["class","row p-3 mb-2 bg-light text-dark",4,"ngFor","ngForOf"],[1,"table-responsive"],[1,"table","table-hover"],[4,"ngFor","ngForOf"],[3,"value"],[1,"row","p-3","mb-2","bg-light","text-dark"],["class","row",4,"ngFor","ngForOf"],["class","col-md-3",4,"ngIf"],["title","","data-toggle","tooltip","data-original-title","Edit",1,"edit",3,"routerLink","queryParams"],[1,"routeIcon"]],template:function(t,e){if(1&t){const t=r.Yb();r.Xb(0,"div",0),r.Xb(1,"div",1),r.Xb(2,"div",2),r.Xb(3,"div",3),r.Xb(4,"div",4),r.Xb(5,"div",5),r.Xb(6,"h4",6),r.Hc(7,"Payment Records "),r.Xb(8,"a",7),r.Xb(9,"mat-icon",8),r.Hc(10,"add"),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Xb(11,"div",9),r.Xb(12,"form",10,11),r.fc("ngSubmit",(function(){return e.onSubmit()})),r.Xb(14,"div",2),r.Xb(15,"div",12),r.Xb(16,"mat-form-field"),r.Xb(17,"mat-label"),r.Hc(18,"From Date/Expanse Date"),r.Wb(),r.Sb(19,"input",13),r.Sb(20,"mat-datepicker-toggle",14),r.Sb(21,"mat-datepicker",null,15),r.Wb(),r.Wb(),r.Xb(23,"div",12),r.Xb(24,"mat-form-field"),r.Xb(25,"mat-label"),r.Hc(26,"To Date"),r.Wb(),r.Sb(27,"input",16),r.Sb(28,"mat-datepicker-toggle",14),r.Sb(29,"mat-datepicker",null,17),r.Wb(),r.Wb(),r.Xb(31,"div",18),r.Xb(32,"mat-form-field",19),r.Xb(33,"mat-select",20),r.Gc(34,g,2,2,"mat-option",21),r.Wb(),r.Wb(),r.Wb(),r.Xb(35,"div",12),r.Xb(36,"button",22),r.fc("click",(function(){return r.Ac(t),r.xc(13).ngSubmit.emit()})),r.Hc(37,"Search"),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Xb(38,"div",9),r.Xb(39,"div",23),r.Gc(40,I,8,4,"div",24),r.jc(41,"keyvalue"),r.Wb(),r.Wb(),r.Xb(42,"div",9),r.Xb(43,"div",25),r.Xb(44,"table",26),r.Xb(45,"thead",23),r.Xb(46,"th"),r.Hc(47,"Payment Id"),r.Wb(),r.Xb(48,"th"),r.Hc(49,"Date"),r.Wb(),r.Xb(50,"th"),r.Hc(51,"Project Payment"),r.Wb(),r.Xb(52,"th"),r.Hc(53,"Other Payment"),r.Wb(),r.Xb(54,"th"),r.Hc(55,"Total Payment"),r.Wb(),r.Xb(56,"th"),r.Hc(57,"Notes"),r.Wb(),r.Wb(),r.Gc(58,D,21,20,"tbody",27),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Wb()}if(2&t){const t=r.xc(13),a=r.xc(22),i=r.xc(30);r.Cb(12),r.oc("formGroup",e.payForm),r.Cb(7),r.oc("matDatepicker",a),r.Cb(1),r.oc("for",a),r.Cb(7),r.oc("matDatepicker",i),r.Cb(1),r.oc("for",i),r.Cb(6),r.oc("ngForOf",e.clients),r.Cb(2),r.oc("disabled",!t.form.valid),r.Cb(4),r.oc("ngForOf",r.kc(41,9,e.paymentView)),r.Cb(18),r.oc("ngForOf",e.payments)}},directives:[n.e,m.a,o.p,o.l,o.f,p.c,p.f,u.b,o.a,h.b,o.k,o.d,h.d,p.g,h.a,f.a,i.l,y.l,i.m],pipes:[i.g,i.c],styles:[""]}),t})();var F=a("ro1u"),k=a("5Iue");class H{}var P=a("dbUT");function N(t,e){if(1&t&&(r.Xb(0,"mat-option",60),r.Hc(1),r.Wb()),2&t){const t=e.$implicit;r.oc("value",t.value),r.Cb(1),r.Jc(" ",t.viewValue," ")}}function x(t,e){1&t&&r.Sb(0,"mat-error",61)}function M(t,e){1&t&&(r.Xb(0,"mat-error"),r.Hc(1,"Transaction date is required"),r.Wb())}function R(t,e){if(1&t&&(r.Xb(0,"mat-option",60),r.Hc(1),r.Wb()),2&t){const t=e.$implicit;r.oc("value",t.clientId),r.Cb(1),r.Jc(" ",t.clientName," ")}}function A(t,e){1&t&&(r.Xb(0,"mat-error"),r.Hc(1,"Client is required"),r.Wb())}function j(t,e){if(1&t){const t=r.Yb();r.Xb(0,"tbody"),r.Xb(1,"tr"),r.Xb(2,"td"),r.Hc(3),r.Wb(),r.Xb(4,"td"),r.Hc(5),r.jc(6,"currency"),r.Wb(),r.Xb(7,"td"),r.Hc(8),r.jc(9,"currency"),r.Wb(),r.Xb(10,"td"),r.Hc(11),r.Wb(),r.Xb(12,"td"),r.Xb(13,"a",62),r.fc("click",(function(){r.Ac(t);const a=e.$implicit,i=r.ic(),n=r.xc(78);return i.editDetail(a,n)})),r.Xb(14,"mat-icon",63),r.Hc(15,"edit"),r.Wb(),r.Wb(),r.Xb(16,"a",64),r.fc("click",(function(){r.Ac(t);const a=e.$implicit;return r.ic().openDialog(a)})),r.Xb(17,"mat-icon",63),r.Hc(18,"delete"),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Wb()}if(2&t){const t=e.$implicit;r.Cb(3),r.Ic(t.itemId),r.Cb(2),r.Ic(r.lc(6,4,t.projectAmount,"INR")),r.Cb(3),r.Ic(r.lc(9,7,t.paidAmount,"INR")),r.Cb(3),r.Ic(t.amount)}}function O(t,e){1&t&&(r.Xb(0,"mat-error"),r.Hc(1,"Amount is required"),r.Wb())}let G=(()=>{class t{constructor(t,e,a,i,n,o,r){this.payService=t,this.dialog=e,this.route=a,this.notificationService=i,this.fb=n,this.clientService=o,this.datePipe=r,this.details=[],this.clients=[],this.otherPayment=0,this.totalpayment=0,this.amount=0,this.paidAmount=0,this.paymentTypes=[{value:"SEND",viewValue:"Pay"},{value:"RECEIVE",viewValue:"Receive"}],this.records=[],this.hasError=(t,e)=>this.payForm.controls[t].hasError(e),this.hasDetailError=(t,e)=>this.payDetailsForm.controls[t].hasError(e)}ngOnInit(){this.getClients(),this.payForm=this.fb.group({paymentId:new o.c(null),paymentDate:new o.c("",o.o.required),paymentDateString:new o.c(null),clientId:new o.c("",o.o.required),projectPayment:new o.c(""),otherPayment:new o.c(0),totalPayment:new o.c(""),notes:new o.c(""),paymentType:new o.c("",o.o.required),details:new o.c("")}),this.payDetailsForm=this.fb.group({paymentId:new o.c(""),paymentDetailId:new o.c(""),itemId:new o.c("",o.o.required),projectAmount:new o.c(""),amount:new o.c("",o.o.required),paidAmount:new o.c(""),deliveryId:new o.c(""),invoiceId:new o.c("")}),this.route.queryParams.subscribe(t=>{Object.keys(t).length>0&&this.payService.findById(t.id).subscribe(t=>{this.details=t.details,t.paymentDate=Object(k.a)(t.paymentDateString),this.payForm.setValue(t)})})}onSubmit(){this.payForm.valid&&(this.pay=this.payForm.value,this.pay.details=this.details,null!=this.payForm.value.paymentDate&&(this.pay.paymentDateString=this.datePipe.transform(this.payForm.value.paymentDate,"dd/MM/yyyy")),this.payService.save(this.pay).subscribe(t=>{this.notificationService.openSnackBar(t.message,t.status),console.log("success response ::"),console.log(t),this.payForm.reset(),this.details=[]},t=>{this.notificationService.openSnackBar(t.error.message,t.error.status),console.log("error response ::"),console.log(t.message)}))}onDetailSubmit(t){if(this.payDetailsForm.valid){let e=this.payDetailsForm.value,a=-1;a=this.details.findIndex(t=>t.itemId==e.itemId),console.log("last index >>"+a),-1!=a?(this.details[a]=this.payDetailsForm.value,this.notificationService.openSnackBar("Details updated successfully","success")):(this.details.push(this.payDetailsForm.value),this.notificationService.openSnackBar("Details added successfully","success")),this.calculateHeader(),this.payDetailsForm.reset(),t.hide()}else this.notificationService.openSnackBar("Error occurred, please review and submit again","danger")}close(t){this.payDetailsForm.reset(),t.hide()}editDetail(t,e){this.payDetailsForm.setValue(t),e.show()}openDialog(t){this.dialog.open(F.a,{width:"250px",data:{header:"Confirm",content:"Are you sure to delete?"}}).afterClosed().subscribe(e=>{e&&this.deleteDetail(t)})}deleteDetail(t){let e=this.calculateDetailIndex(t);t.paymentDetailId?this.payService.deleteDetail(t.paymentId,t.paymentDetailId).subscribe(t=>{this.details.splice(e,1),this.calculateHeader(),this.notificationService.openSnackBar(t.message,t.status)},t=>{this.notificationService.openSnackBar(t.error.message,t.error.status),console.log("error response ::"),console.log(t.message)}):-1!==e&&(this.details.splice(e,1),this.notificationService.openSnackBar("Details removed successfully","success"))}calculateDetailIndex(t){let e=-1;return e=this.details.findIndex(e=>e.paymentDetailId==t.paymentDetailId),e}getClients(){this.clientService.findAll().subscribe(t=>{this.clients=t})}isMobileMenu(){return!($(window).width()>991)}modalWIdth(){return this.isMobileMenu()?"100%":"150%"}populatDetailLists(t){this.payService.findOrdersByClient(t.value,this.paymentType).subscribe(t=>{this.records=t,this.details=this.records.map(t=>this.populateRow(t))})}populateRow(t){let e=new H;return e.itemId=t.referenceId,e.projectAmount=t.totalAmount,e.paymentId="",e.paymentDetailId=null,e.amount=0,e.paidAmount=t.paidAmount,e.deliveryId="",e.invoiceId="",e}calculateHeader(){this.projectPayment=this.details.map(t=>t.amount).reduce((t,e)=>Number.parseFloat(t.toString())+Number.parseFloat(e.toString()),0),this.totalpayment=this.projectPayment,null!=this.otherPayment&&(this.totalpayment=Number.parseFloat(this.projectPayment.toString())+Number.parseFloat(this.otherPayment.toString()))}resetGrid(){this.details=[]}}return t.\u0275fac=function(e){return new(e||t)(r.Rb(l),r.Rb(d.b),r.Rb(n.a),r.Rb(s.a),r.Rb(o.b),r.Rb(b.a),r.Rb(i.e))},t.\u0275cmp=r.Lb({type:t,selectors:[["app-pay"]],decls:111,vars:24,consts:[[1,"main-content"],[1,"container-fluid"],[1,"row"],[1,"col-md-12"],[1,"card"],[1,"card-header","card-header-success"],[1,"card-title"],["routerLink","/payment/payments"],[1,"gridIcon"],[1,"card-category"],[1,"card-body"],[3,"formGroup","ngSubmit"],["headerForm","ngForm"],[1,"col-md-2"],[1,"invisible","example-full-width"],["matInput","","placeholder","paymentId","formControlName","paymentId"],[1,"example-full-width"],["placeholder","Transaction Type","formControlName","paymentType",3,"ngModel","ngModelChange","selectionChange"],[3,"value",4,"ngFor","ngForOf"],["Transaction","","type","","is","","required","",4,"ngIf"],["matInput","","formControlName","paymentDate",3,"matDatepicker"],["matSuffix","",3,"for"],["picker3",""],[4,"ngIf"],[1,"col-md-3"],["placeholder","Client","formControlName","clientId",3,"selectionChange"],[1,"col-md-5"],["matInput","","placeholder","Notes","formControlName","notes"],["matInput","","placeholder","Payment from projects","formControlName","projectPayment",2,"text-align","right",3,"ngModel","readonly","ngModelChange","change"],["matInput","","placeholder","Other Payments","formControlName","otherPayment",2,"text-align","right",3,"ngModel","ngModelChange","change"],["matInput","","placeholder","Total Payments","formControlName","totalPayment",2,"text-align","right",3,"ngModel","readonly","ngModelChange","change"],[1,"card","card-plain"],[1,"card-header","card-header-info"],["width","100%"],["width","90%"],[1,"table-responsive"],[1,"table","table-hover"],[1,"text-primary"],[4,"ngFor","ngForOf"],["mat-raised-button","","type","submit",1,"btn","btn-success","pull-right",3,"disabled","click"],[1,"clearfix"],["mdbModal","","id","frameModalTop","tabindex","-1","role","dialog","aria-labelledby","myModalLabel","aria-hidden","true",1,"modal","fade","left"],["frame","mdbModal"],["role","document",1,"modal-dialog"],[1,"modal-content"],[1,"modal-header","text-center"],[1,"modal-title","w-100","font-weight-bold"],["type","button","data-dismiss","modal","aria-label","Close",1,"close",3,"click"],["aria-hidden","true"],[3,"formGroup"],[1,"col-md-4"],["matInput","","placeholder","paymentDetailId","formControlName","paymentDetailId"],["matInput","","placeholder","deliveryId","formControlName","deliveryId"],["matInput","","placeholder","invoiceId","formControlName","invoiceId"],["matInput","","placeholder","Transaction Id","formControlName","itemId",3,"readonly"],["matInput","","placeholder","Billed Amount","formControlName","projectAmount",2,"text-align","right",3,"readonly"],["matInput","","placeholder","Already Paid/Received","formControlName","paidAmount",2,"text-align","right",3,"readonly"],["matInput","","placeholder","Paid Amount","formControlName","amount",2,"text-align","right",3,"ngModel","ngModelChange","change"],[1,"modal-footer","d-flex","justify-content-right"],["mdbBtn","","color","deep-orange","mdbWavesEffect","",1,"waves-light",3,"click"],[3,"value"],["Transaction","","type","","is","","required",""],["title","","data-toggle","tooltip","data-original-title","Edit",1,"edit",2,"cursor","pointer",3,"click"],[1,"routeIcon"],["title","","data-toggle","tooltip","data-original-title","Delete",1,"delete",2,"cursor","pointer",3,"click"]],template:function(t,e){if(1&t){const t=r.Yb();r.Xb(0,"div",0),r.Xb(1,"div",1),r.Xb(2,"div",2),r.Xb(3,"div",3),r.Xb(4,"div",4),r.Xb(5,"div",5),r.Xb(6,"h4",6),r.Hc(7,"Payment "),r.Xb(8,"a",7),r.Xb(9,"mat-icon",8),r.Hc(10,"web"),r.Wb(),r.Wb(),r.Wb(),r.Xb(11,"p",9),r.Hc(12,"Record Payment Records here"),r.Wb(),r.Wb(),r.Xb(13,"div",10),r.Xb(14,"form",11,12),r.fc("ngSubmit",(function(){return e.onSubmit()})),r.Xb(16,"div",2),r.Xb(17,"div",13),r.Xb(18,"mat-form-field",14),r.Sb(19,"input",15),r.Wb(),r.Xb(20,"mat-form-field",16),r.Xb(21,"mat-select",17),r.fc("ngModelChange",(function(t){return e.paymentType=t}))("selectionChange",(function(){return e.resetGrid()})),r.Gc(22,N,2,2,"mat-option",18),r.Wb(),r.Gc(23,x,1,0,"mat-error",19),r.Wb(),r.Wb(),r.Xb(24,"div",13),r.Xb(25,"mat-form-field"),r.Xb(26,"mat-label"),r.Hc(27,"Transaction Date"),r.Wb(),r.Sb(28,"input",20),r.Sb(29,"mat-datepicker-toggle",21),r.Sb(30,"mat-datepicker",null,22),r.Gc(32,M,2,0,"mat-error",23),r.Wb(),r.Wb(),r.Xb(33,"div",24),r.Xb(34,"mat-form-field",16),r.Xb(35,"mat-select",25),r.fc("selectionChange",(function(t){return e.populatDetailLists(t)})),r.Gc(36,R,2,2,"mat-option",18),r.Wb(),r.Gc(37,A,2,0,"mat-error",23),r.Wb(),r.Wb(),r.Xb(38,"div",26),r.Xb(39,"mat-form-field",16),r.Sb(40,"input",27),r.Wb(),r.Wb(),r.Wb(),r.Xb(41,"div",2),r.Xb(42,"div",13),r.Xb(43,"mat-form-field",16),r.Xb(44,"input",28),r.fc("ngModelChange",(function(t){return e.projectPayment=t}))("change",(function(){return e.calculateHeader()})),r.Wb(),r.Wb(),r.Wb(),r.Xb(45,"div",13),r.Xb(46,"mat-form-field",16),r.Xb(47,"input",29),r.fc("ngModelChange",(function(t){return e.otherPayment=t}))("change",(function(){return e.calculateHeader()})),r.Wb(),r.Wb(),r.Wb(),r.Xb(48,"div",13),r.Xb(49,"mat-form-field",16),r.Xb(50,"input",30),r.fc("ngModelChange",(function(t){return e.totalpayment=t}))("change",(function(){return e.calculateHeader()})),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Xb(51,"div",2),r.Xb(52,"div",3),r.Xb(53,"div",31),r.Xb(54,"div",32),r.Xb(55,"table",33),r.Xb(56,"tr"),r.Xb(57,"td",34),r.Xb(58,"h4",6),r.Hc(59,"Payment Details"),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Sb(60,"p",9),r.Wb(),r.Xb(61,"div",10),r.Xb(62,"div",35),r.Xb(63,"table",36),r.Xb(64,"thead",37),r.Xb(65,"th"),r.Hc(66,"Invoice Id"),r.Wb(),r.Xb(67,"th"),r.Hc(68,"Billed Amount"),r.Wb(),r.Xb(69,"th"),r.Hc(70,"Alreaid Paid/Received"),r.Wb(),r.Xb(71,"th"),r.Hc(72,"Amount"),r.Wb(),r.Wb(),r.Gc(73,j,19,10,"tbody",38),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Xb(74,"button",39),r.fc("click",(function(){return r.Ac(t),r.xc(15).ngSubmit.emit()})),r.Hc(75,"Save"),r.Wb(),r.Sb(76,"div",40),r.Xb(77,"div",41,42),r.Xb(79,"div",43),r.Xb(80,"div",44),r.Xb(81,"div",45),r.Xb(82,"h4",46),r.Hc(83,"Add/Update Work Log"),r.Wb(),r.Xb(84,"button",47),r.fc("click",(function(){r.Ac(t);const a=r.xc(78);return e.close(a)})),r.Xb(85,"span",48),r.Hc(86,"\xd7"),r.Wb(),r.Wb(),r.Wb(),r.Xb(87,"div",10),r.Xb(88,"form",49),r.Xb(89,"div",2),r.Xb(90,"div",50),r.Xb(91,"mat-form-field",14),r.Sb(92,"input",15),r.Sb(93,"input",51),r.Sb(94,"input",52),r.Sb(95,"input",53),r.Wb(),r.Xb(96,"mat-form-field",16),r.Sb(97,"input",54),r.Wb(),r.Wb(),r.Xb(98,"div",13),r.Xb(99,"mat-form-field",16),r.Sb(100,"input",55),r.Wb(),r.Wb(),r.Xb(101,"div",13),r.Xb(102,"mat-form-field",16),r.Sb(103,"input",56),r.Wb(),r.Wb(),r.Xb(104,"div",13),r.Xb(105,"mat-form-field",16),r.Xb(106,"input",57),r.fc("ngModelChange",(function(t){return e.amount=t}))("change",(function(){return e.calculateHeader()})),r.Wb(),r.Gc(107,O,2,0,"mat-error",23),r.Wb(),r.Wb(),r.Wb(),r.Xb(108,"div",58),r.Xb(109,"button",59),r.fc("click",(function(){r.Ac(t);const a=r.xc(78);return e.onDetailSubmit(a)})),r.Hc(110,"Save"),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Wb(),r.Wb()}if(2&t){const t=r.xc(15),a=r.xc(31);r.Cb(14),r.oc("formGroup",e.payForm),r.Cb(7),r.oc("ngModel",e.paymentType),r.Cb(1),r.oc("ngForOf",e.paymentTypes),r.Cb(1),r.oc("ngIf",e.hasError("paymentType","required")),r.Cb(5),r.oc("matDatepicker",a),r.Cb(1),r.oc("for",a),r.Cb(3),r.oc("ngIf",e.hasError("paymentDate","required")),r.Cb(4),r.oc("ngForOf",e.clients),r.Cb(1),r.oc("ngIf",e.hasError("clientId","required")),r.Cb(7),r.oc("ngModel",e.projectPayment)("readonly",!0),r.Cb(3),r.oc("ngModel",e.otherPayment),r.Cb(3),r.oc("ngModel",e.totalpayment)("readonly",!0),r.Cb(23),r.oc("ngForOf",e.details),r.Cb(1),r.oc("disabled",!t.form.valid),r.Cb(6),r.Fc("width",e.modalWIdth()),r.Cb(8),r.oc("formGroup",e.payDetailsForm),r.Cb(9),r.oc("readonly",!0),r.Cb(3),r.oc("readonly",!0),r.Cb(3),r.oc("readonly",!0),r.Cb(3),r.oc("ngModel",e.amount),r.Cb(1),r.oc("ngIf",e.hasDetailError("amount","required"))}},directives:[n.e,m.a,o.p,o.l,o.f,p.c,u.b,o.a,o.k,o.d,f.a,i.l,i.m,p.f,h.b,h.d,p.g,h.a,P.c,P.b,y.l,p.b],pipes:[i.c],styles:[""]}),t})();const T=[{path:"payment/add",component:G},{path:"payment/edit",component:G},{path:"payments",component:w}];let E=(()=>{class t{}return t.\u0275mod=r.Pb({type:t}),t.\u0275inj=r.Ob({factory:function(e){return new(e||t)},imports:[[n.f.forChild(T)],n.f]}),t})();var q=a("A5z7"),B=a("MutI"),L=a("STbY"),V=a("QibW"),J=a("XhcP"),Y=a("dNgK"),K=a("+0xr"),U=a("wZkO");let z=(()=>{class t{}return t.\u0275mod=r.Pb({type:t}),t.\u0275inj=r.Ob({factory:function(e){return new(e||t)},imports:[[i.b,E,B.b,L.a,m.b,u.c,J.b,c.c,o.n,o.g,Y.c,V.a,f.b,U.a,d.f,K.a,h.c,[P.d.forRoot()],P.f,P.e,P.a,y.j,y.q,q.c]]}),t})()}}]);