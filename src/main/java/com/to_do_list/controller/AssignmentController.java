package com.to_do_list.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.to_do_list.common.Result;
import com.to_do_list.domain.Assignment;
import com.to_do_list.service.AssignmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/assignment")
@Slf4j
public class AssignmentController {
    @Autowired
    private AssignmentService assignmentService;

    /**
     * Get all data in assignment table
     * @return Result containing assignment data
     */
    @GetMapping
    public Result<List<Assignment>> getAll(HttpSession session) {
        log.info("Retrieving all assignments");

        //Query: SELECT * FROM assignment WHERE user_id = ? ORDER BY status DESC,due_date ASC
        LambdaQueryWrapper<Assignment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Long userId = (Long) session.getAttribute("user");
        lambdaQueryWrapper.eq(Assignment::getUserId, userId);
        lambdaQueryWrapper.orderByDesc(Assignment::getStatus).orderByAsc(Assignment::getDueDate);

        List<Assignment> assignments = assignmentService.list(lambdaQueryWrapper);

        return Result.success(assignments);
    }

    /**
     * Adding an assignment
     * @param session Stores user id
     * @param assignment New assignment data
     * @return Success message
     */
    @PostMapping
    public Result<String> save(HttpSession session,
                               @RequestBody Assignment assignment) {
        log.info("Saving assignment: {}", assignment.toString());

        Long userId = (Long) session.getAttribute("user");
        assignment.setUserId(userId);
        assignmentService.save(assignment);
        return Result.success("Assignment saved successfully");
    }

    /**
     * Delete an assignment
     * @param id Id of assignment
     * @return Success message
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteById(@PathVariable Long id) {
        log.info("Deleting assignment: {}", id);
        assignmentService.removeById(id);
        return Result.success("Assignment deleted successfully");
    }

    /**
     * Getting an assignment by id
     * @param id Assignment id
     * @return Selected assignment
     */
    @GetMapping("/{id}")
    public Result<Assignment> getById(@PathVariable Long id) {
        log.info("Getting assignment by id {}", id);
        Assignment assignment = assignmentService.getById(id);
        return Result.success(assignment);
    }

    /**
     * Update an assignment's data
     * @param assignment Assignment object
     * @return Success message
     */
    @PutMapping
    public Result<String> updateAssignment(@RequestBody Assignment assignment) {
        log.info("Updating assignment: {}", assignment.toString());
        assignmentService.updateById(assignment);
        return Result.success("Update was successful!");
    }

    @DeleteMapping("/clean")
    public Result<String> deleteCompletedAssignment(HttpSession session) {
        log.info("Deleting completed assignment");
        Long userId = (Long) session.getAttribute("user");

        //Adding conditions: DELETE FROM assignment WHERE user_id = ? AND status = ?
        LambdaQueryWrapper<Assignment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Assignment::getUserId, userId).eq(Assignment::getStatus, 0);

        boolean remove = assignmentService.remove(lambdaQueryWrapper);
        if (remove) {
            return Result.success("Deleted successfully!");
        } else {
            return Result.error("You don't have any completed assignment yet!");
        }
    }
}