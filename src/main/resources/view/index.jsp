<%@include file="partials/header.jsp" %>
        <h1>Hello World!</h1>
        <form action="<c:url value="/player/"></c:url>" method="post">
            <input type="text" name="name" value="Suso" />
            <input type="text" name="transfermarktUrl" value="https://www.transfermarkt.com/suso/profil/spieler/111961" />
            <input type="text" name="whoScoredUrl" value="https://www.whoscored.com/Players/105591/Show/Suso" />
            <input type="text" name="pesDbUrl" value="http://pesdb.net/pes2018/?id=45267" />
            <input type="text" name="psmlUrl" value="http://psml.rs/?action=shwply&playerID=45267" />
            <input type="checkbox" name="myPlayer"> 
            <button>Snimi</button>
        </form>
            <select>
            <c:forEach items="${players}" var="player">
                <option>${player.name}</option>
            </c:forEach>
            </select>
<%@include file="partials/footer.jsp" %>
