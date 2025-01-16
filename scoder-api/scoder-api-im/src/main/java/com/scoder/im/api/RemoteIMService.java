package com.scoder.im.api;

import com.scoder.common.core.constant.ServiceNameConstants;
import com.scoder.common.core.web.domain.AjaxResult;
import com.scoder.im.api.domain.Group;
import com.scoder.im.api.factory.RemoteIMFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Remote IM (Instant Messaging) Service API.
 * <p>
 * This interface defines methods to interact with the IM service.
 * It uses Feign to simplify remote service calls, including group creation,
 * joining a team, and updating team information.
 * <p>
 * - FeignClient annotations specify the remote service and fallback mechanism.
 * - Methods are mapped to the respective endpoints in the IM service.
 *
 * @FeignClient: - contextId: Unique identifier for the Feign client in the application context.
 * - value: The service name registered in the service registry.
 * - fallbackFactory: Specifies the fallback factory to handle failures.
 * @Author Shawn Cui
 */
@FeignClient(
        contextId = "remoteIMService", // Unique identifier for the Feign client
        value = ServiceNameConstants.IM_SERVICE, // Name of the IM service in the registry
        fallbackFactory = RemoteIMFallbackFactory.class // Fallback factory for handling service failures
)
public interface RemoteIMService {

    /**
     * Create a new group.
     * <p>
     * Sends a POST request to the IM service to create a group.
     *
     * @param group the group information to be created
     * @return an AjaxResult containing the created group details
     */
    @PostMapping("/group/createGroup")
    public AjaxResult<Group> createGroup(@RequestBody Group group);

    /**
     * Join a team.
     * <p>
     * Sends a POST request to allow a user to join a specified team.
     *
     * @param teamId the ID of the team to join
     * @param userId the ID of the user attempting to join
     * @return an AjaxResult indicating the success or failure of the operation
     */
    @PostMapping("/joinTeam/{teamId}/{userId}")
    public AjaxResult joinTeam(@PathVariable("teamId") Long teamId, @PathVariable("userId") Long userId);

    /**
     * Update team information.
     * <p>
     * Sends a PUT request to update the details of an existing team.
     *
     * @param group the updated group information
     * @return an AjaxResult indicating the success or failure of the update
     */
    @PutMapping("/updateTeam")
    public AjaxResult updateTeam(@RequestBody Group group);
}