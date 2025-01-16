package com.scoder.im.service;

import com.scoder.im.api.domain.Group;
import com.scoder.im.domain.vos.GroupVo;
import com.scoder.im.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * GroupService manages group-related operations such as creating groups,
 * adding members, and removing members. It serves as the service layer
 * for group management, interacting with the GroupRepository and handling
 * group-specific business logic.
 * <p>
 * This service ensures that group creation and modification are performed
 * securely and consistently.
 *
 * @author Shawn Cui
 */
@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository; // Repository for managing group data


    /**
     * Creates a new group with the provided details. The current user is set
     * as the creator, and the group creation timestamp is recorded.
     *
     * @param group The group object containing the group's details.
     * @return The newly created group after being saved to the database.
     */
    public Group createGroup(Group group) {
        group.setTimestamp(System.currentTimeMillis()); // Set creation timestamp
        return groupRepository.save(group);
    }

    /**
     * Adds a member to an existing group.
     *
     * @param groupId  The ID of the group to which the member will be added.
     * @param memberId The ID of the member to be added to the group.
     * @throws IllegalArgumentException If the group is not found.
     */
    public void addMemberToGroup(Long groupId, Long memberId) {
        Group group = groupRepository.findById(groupId.toString())
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        group.getMemberIds().add(memberId); // Add the member to the group
        groupRepository.save(group); // Save the updated group
    }

    /**
     * Removes a member from an existing group.
     *
     * @param groupId  The ID of the group from which the member will be removed.
     * @param memberId The ID of the member to be removed from the group.
     * @throws IllegalArgumentException If the group is not found.
     */
    public void removeMemberFromGroup(Long groupId, Long memberId) {
        Group group = groupRepository.findById(groupId.toString())
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        group.getMemberIds().remove(memberId); // Remove the member from the group
        groupRepository.save(group); // Save the updated group
    }

    public List<GroupVo> getTeamChatList(Long userId) {
        return groupRepository.getTeamChatList(userId);
    }

    public Group joinTeam(Long teamId, Long userId) {
        Group group = groupRepository.findByTeamId(teamId);
        group.getMemberIds().add(userId);
        return groupRepository.save(group);
    }

    public String updateTeam(Group group) {
        if (group == null || group.getTeamId() == null) {
            return "Team ID or Group data is missing";
        }

        Group existingGroup = groupRepository.findByTeamId(group.getTeamId());
        if (existingGroup == null) {
            return "Team not found";
        }

        existingGroup.setName(group.getName());
        existingGroup.setDescription(group.getDescription());
        existingGroup.setAvatar(group.getAvatar());

        try {
            groupRepository.save(existingGroup);
            return "Team updated successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to update team: " + e.getMessage();
        }
    }
}