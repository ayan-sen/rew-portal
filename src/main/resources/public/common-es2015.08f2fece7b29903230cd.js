(window.webpackJsonp=window.webpackJsonp||[]).push([[1],{"+hj4":function(t,n,e){"use strict";e.d(n,"a",(function(){return i}));var r=e("fXoL"),a=e("tk/3"),o=e("BPD/");let i=(()=>{class t{constructor(t,n){this.http=t,this.errorHandler=n}save(t){return this.http.post("admin/raw-material",t)}findAll(){return this.http.get("admin/materials")}findAllProducts(){return this.http.get("admin/products")}findAllRawMaterials(){return this.http.get("admin/raw-materials")}delete(t){return this.http.delete("admin/materials/"+t)}}return t.\u0275fac=function(n){return new(n||t)(r.bc(a.b),r.bc(o.a))},t.\u0275prov=r.Nb({token:t,factory:t.\u0275fac,providedIn:"root"}),t})()},"BPD/":function(t,n,e){"use strict";e.d(n,"a",(function(){return o}));var r=e("z6cu"),a=e("fXoL");let o=(()=>{class t{constructor(){}handleError(t){return t.error instanceof ErrorEvent?console.error("An error occurred:",t.error.message):console.error(`Backend returned code ${t.status}, `+`body was: ${t.error}`),Object(r.a)("Something bad happened; please try again later.")}}return t.\u0275fac=function(n){return new(n||t)},t.\u0275prov=a.Nb({token:t,factory:t.\u0275fac,providedIn:"root"}),t})()},Deg8:function(t,n,e){"use strict";e.d(n,"a",(function(){return i}));var r=e("fXoL"),a=e("tk/3"),o=e("BPD/");let i=(()=>{class t{constructor(t,n){this.http=t,this.errorHandler=n}save(t){return this.http.post("admin/client",t)}findAll(){return this.http.get("admin/clients")}findById(t){return this.http.get("admin/clients/"+t)}deleteClient(t){return this.http.delete("admin/clients/"+t)}deleteClientDetail(t,n){return this.http.delete("admin/clients/"+t+"/detail/"+n)}}return t.\u0275fac=function(n){return new(n||t)(r.bc(a.b),r.bc(o.a))},t.\u0275prov=r.Nb({token:t,factory:t.\u0275fac,providedIn:"root"}),t})()},ShJT:function(t,n,e){"use strict";e.d(n,"a",(function(){return o}));var r=e("fXoL"),a=e("tk/3");let o=(()=>{class t{constructor(t){this.http=t}addUnit(t){return this.http.put("admin/unit",t)}getAllUnits(){return this.http.get("admin/units")}}return t.\u0275fac=function(n){return new(n||t)(r.bc(a.b))},t.\u0275prov=r.Nb({token:t,factory:t.\u0275fac,providedIn:"root"}),t})()},ro1u:function(t,n,e){"use strict";e.d(n,"a",(function(){return o}));var r=e("0IaG"),a=e("fXoL");let o=(()=>{class t{constructor(t,n){this.dialogRef=t,this.data=n}ngOnInit(){}onNoClick(){this.data.submit=!1,this.dialogRef.close()}onOkClick(){this.data.submit=!0,this.dialogRef.close()}}return t.\u0275fac=function(n){return new(n||t)(a.Rb(r.g),a.Rb(r.a))},t.\u0275cmp=a.Lb({type:t,selectors:[["app-common-dialog"]],decls:10,vars:4,consts:[["mat-dialog-title",""],["mat-dialog-content",""],["mat-dialog-actions","",1,"modal-footer","justify-content-center"],["mat-button","",3,"mat-dialog-close"],["mat-button","","cdkFocusInitial","",3,"mat-dialog-close"]],template:function(t,n){1&t&&(a.Xb(0,"h1",0),a.Hc(1),a.Wb(),a.Xb(2,"div",1),a.Xb(3,"p"),a.Hc(4),a.Wb(),a.Wb(),a.Xb(5,"div",2),a.Xb(6,"button",3),a.Hc(7,"Cancel"),a.Wb(),a.Xb(8,"button",4),a.Hc(9,"Ok"),a.Wb(),a.Wb()),2&t&&(a.Cb(1),a.Ic(n.data.header),a.Cb(3),a.Ic(n.data.content),a.Cb(2),a.oc("mat-dialog-close",!1),a.Cb(2),a.oc("mat-dialog-close",!0))},directives:[r.h,r.e,r.c,r.d],styles:[""]}),t})()},zYZu:function(t,n,e){"use strict";e.d(n,"a",(function(){return o}));var r=e("dNgK"),a=e("fXoL");let o=(()=>{class t{constructor(t){this._snackBar=t,this.durationInSeconds=5}openSnackBar(t,n){let e=new r.b;switch(e.panelClass=["btn-danger"],e.duration=1e3*this.durationInSeconds,e.horizontalPosition="right",n){case"success":e.panelClass=["btn-success"];break;case"failure":e.panelClass=["btn-danger"];break;default:e.panelClass=["btn-info"]}this._snackBar.open(t,"",e)}}return t.\u0275fac=function(n){return new(n||t)(a.bc(r.a))},t.\u0275prov=a.Nb({token:t,factory:t.\u0275fac,providedIn:"root"}),t})()}}]);