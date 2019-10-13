/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.webservice;

import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import phund.entity.TrendGame;
import phund.entity.WrapperTrendGame;
import phund.service.GameService;
import phund.service.GameServiceImp;

/**
 * REST Web Service
 *
 * @author PhuNDSE63159
 */
@Path("/games")
public class GamesResource {

    @Context
    private UriInfo context;
    
    private GameService gameService;

    public GamesResource() {
        gameService = new GameServiceImp();
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/trend")
    public WrapperTrendGame getTrendGames(@QueryParam("offset") Integer offset, @QueryParam("fetch") Integer fetch){
        List<TrendGame> games = gameService.getTrendGames(offset, fetch);
        WrapperTrendGame wrapper = new WrapperTrendGame(games);
        return wrapper;
    }
}
