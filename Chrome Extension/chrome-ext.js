let myLeads=[];


// myLeads=JSON.parse(myLeads)
// myLeads.push("hello");
// console.log(myLeads)
// myLeads=JSON.stringify(myLeads);
// console.log(typeof myLeads);

const inputEl=document.getElementById("input-el");
const inputBtn=document.getElementById("input-btn");
const ulEl=document.getElementById("ul-el");
const deleteBtn=document.getElementById("delete-btn");
const tabBtn=document.getElementById("save-btn");


tabBtn.addEventListener("click",function(){
    chrome.tabs.query({active:true,currentWindow:true},function(tabs){
    console.log(tabs[0].url);
    myLeads.push(tabs[0].url);
    localStorage.setItem("myLeads",JSON.stringify(myLeads));
    render(myLeads)
});
});

let savedLeads=JSON.parse(localStorage.getItem("myLeads"));
if(savedLeads){
    myLeads=savedLeads
    render(myLeads)
}
else{
    console.log();
}
console.log(savedLeads);

function render(leads){
    let listItems="";
    for(let i=0;i<leads.length;i++){
    listItems+=`<li>
    <a target='_blank' href='${leads[i]}'>
    ${leads[i]}
    </a>
    </li>`;
            
}
ulEl.innerHTML=listItems;
}




deleteBtn.addEventListener("dblclick",function(){
    localStorage.clear();
    myLeads=[];
    render(myLeads)

})

// localStorage.setItem("myName","Mohd")
// let gett=localStorage.getItem("myName")
// console.log(gett);

// localStorage.clear()

inputBtn.addEventListener("click",function(){
    myLeads.push(inputEl.value);
    console.log(myLeads);
    inputEl.value="";

    
    localStorage.setItem("myLeads",JSON.stringify(myLeads));
    render(myLeads)
    console.log(localStorage.getItem("myLeads"));
});



