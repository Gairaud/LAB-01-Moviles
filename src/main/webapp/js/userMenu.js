
let userId;
function loaded(){
   isAdmin()
   //loadMyTrips();
   loadFlights();
   userId = sessionStorage.getItem("userId");
   console.log(userId);
   $("#showTrips").on("click", tripsTable);
   $("#showSearch").on("click", searchTripsTables);
   $("#searchTrips").hide();  
}

async function loadMyTrips(){
    let userId = sessionStorage.getItem("userId");
    let user = {
        id : userId
    }
    let requestBody = {
        method: "POST",
        body: JSON.stringify(user),
        headers: {'Content-Type': 'application/json'}
    }
    let response = await fetch("http://localhost:9393/get-tickets", requestBody);
    let tickets = await response.json()
    console.log(tickets);
    //listTickets(tickets);
    

}

function listReservations(reservations){
    let list=$("#tList");
    list.html("");
    reservations.forEach((t)=>{ if(t.user.id.toString() === userId){reservationRow(list,t);}});
}
function reservationRow(list, t){
    let tr =$("<tr class='d-flex' />");
    tr.html(
        "<td class='col-1'>"+t.id+"</td>"+
        "<td class='col-3'>"+t.flight.route.id+"</td>"+
        "<td class='col-2'>"+t.flight.departureDate+"</td>"+
        "<td class='col-2'>"+t.flight.returnDate+"</td>"+
        "<td class='col-2'>"+t.seatQuantity+"</td>"+
        "<td class='col-1'>"+t.flight.price+"</td>"+
        "<td class=\"col-1\" id='details'><i style='cursor: pointer;' class='fas fa-info'></i></td>"
        
    );
    list.append(tr);
}
function isAdmin(){
    if(sessionStorage.getItem("isAdmin") == 1)
        $("#admin").css("visibility", "visible"); 
}

function tripsTable(){
    $("#searchTrips").hide();      
    $("#trips").show();
      
}
function searchTripsTables(){
    $("#trips").hide();    
    $("#searchTrips").show();
      
}


async function loadFlights(){

    let requestBody = {
        method: "POST",
        headers: {'Content-Type': 'application/json'}
    }
    let response = await fetch("http://localhost:9393/get-flights", requestBody);
    let flights = await response.json()
    console.log(flights);
    listFlights(flights);
    

}

function listFlights(flight){
    let list=$("#fList");
    list.html("");
    flight.forEach((f)=>{ flightRow(list,f);});
}
function flightRow(list, f){
    let tr =$("<tr class='d-flex' />");
    tr.html(
        "<th scope=\"col\" class=\"col-1\">"+f.id+"</th>"+
        
        "<th scope=\"col\" class=\"col-2\">"+f.route.id+"</th>"+
        
        "<th scope=\"col\" class=\"col-3\">"+f.departureDate+"</th>"+
        
        "<th scope=\"col\" class=\"col-3\">"+f.returnDate+"</th>"+
        
        "<th scope=\"col\" class=\"col-2\">"+f.price+"</th>"+
        
        "<td class=\"col-1\" id='buy'><i style='cursor: pointer;' class='fas fa-plane-departure'></i></td>"
        );
        tr.find("#buy").on("click", () => { buyticket(f); });
    list.append(tr);
}

function buyticket(flight){
    sessionStorage.setItem("flightId", flight.id);
    location.href="teste.html";
}

export {listReservations};

$(loaded);