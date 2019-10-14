/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.webservice;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import phund.constant.Constant;
import phund.entity.Game;
import phund.entity.SuggestedGame;
import phund.entity.TrendGame;
import phund.entity.User;
import phund.entity.VotedGame;
import phund.entity.WrapperSuggestedGame;
import phund.entity.WrapperTrendGame;
import phund.entity.WrapperVotedGame;
import phund.service.GameService;
import phund.service.GameServiceImp;

/**
 * REST Web Service
 *
 * @author PhuNDSE63159
 */
@Path("games")
public class GamesResource {

    @Context
    private UriInfo context;

    @Resource
    private WebServiceContext wsContext;

    @Context
    private HttpServletRequest request;

    private GameService gameService;

    public GamesResource() {
        gameService = new GameServiceImp();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/trend")
    public WrapperTrendGame getTrendGames(@QueryParam("offset") Integer offset, @QueryParam("fetch") Integer fetch) {
        List<TrendGame> games = gameService.getTrendGames(offset, fetch);
        WrapperTrendGame wrapper = new WrapperTrendGame(games);
        return wrapper;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/voted")
    public WrapperVotedGame getVotedGames(@QueryParam("offset") Integer offset, @QueryParam("fetch") Integer fetch) {
        HttpSession session = request.getSession(false);
        WrapperVotedGame wrapper = new WrapperVotedGame();
        if (session != null) {
            User user = (User) session.getAttribute(Constant.USER);
            List<VotedGame> games = gameService.getVotedGames(user.getId(), offset, fetch);
            wrapper.setGames(games);
        }
        return wrapper;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/suggested")
    public WrapperSuggestedGame getSuggestedGames(@QueryParam("offset") Integer offset,
            @QueryParam("fetch") Integer fetch) {
        HttpSession session = request.getSession(false);
        WrapperSuggestedGame wrapper = new WrapperSuggestedGame();
        if (session != null) {
            User user = (User) session.getAttribute(Constant.USER);
            List<SuggestedGame> games = gameService.getSuggestedGame(user.getId(), offset, fetch);
            wrapper.setGames(games);
        }
        return wrapper;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Game getGameById(@QueryParam("id") Integer id) {
        Game game = new Game();
        if (id != null && id > 0) {
            game = gameService.getGameById(id);
        }
        return game;
    }

}
