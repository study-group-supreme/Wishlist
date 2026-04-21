package wishlist.service;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wishlist.exception.BadRequestException;
import wishlist.exception.DatabaseOperationException;
import wishlist.exception.DuplicateMemberException;
import wishlist.exception.NotFoundException;
import wishlist.model.Member;
import wishlist.repository.MemberRepository;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public Member getById(int id) {
        if (id <= 0) {
            throw new BadRequestException("Invalid member id");
        }

        try {
            return memberRepository.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Member not found with id: " + id);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Database error while loading member", e);
        }
    }

    public Member getByUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new BadRequestException("Username cannot be empty");
        }

        try {
            Member member = memberRepository.findByUsername(username);
            if (member == null) {
                throw new NotFoundException("No account found with username: " + username);
            }
            return member;
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Database error while searching by username", e);
        }
    }

    public Member getByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new BadRequestException("Email cannot be empty");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new BadRequestException("Invalid email format");
        }


        try {
            Member member = memberRepository.findByEmail(email);
            if (member == null) {
                throw new NotFoundException("No account found with email: " + email);
            }
            return member;
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Database error while searching by email", e);
        }
    }

    @Transactional
    public Member create(Member member) {
        // TODO: are these checked for at template-level actually???
//        if (member.getUsername() == null || member.getUsername().isBlank()) {
//            throw new BadRequestException("Username cannot be empty");
//        }
//        if (member.getPassword() == null || member.getPassword().isBlank()) {
//            throw new BadRequestException("Password cannot be empty");
//        }
//        if (member.getName() == null || member.getName().isBlank()) {
//            throw new BadRequestException("Name cannot be empty");
//        }
//        if (member.getEmail() == null || member.getEmail().isBlank()) {
//            throw new BadRequestException("Email cannot be empty");
//        }
//        if (!member.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
//            throw new BadRequestException("Invalid email format");
//        }

        try {
            memberRepository.insertMember(member);
            return memberRepository.findByUsername(member.getUsername());
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateMemberException(e.getMessage());
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("User creation failed", e);
        }
    }

    @Transactional
    public Member update(Member member) {
        Member existing = getById(member.getId());

            // TODO: is this needed? is it checked for on template-level?
//        if (member.getUsername() == null || member.getUsername().isBlank()) {
//            throw new BadRequestException("Username cannot be empty");
//        }
//        if (member.getPassword() == null || member.getPassword().isBlank()) {
//            throw new BadRequestException("Password cannot be empty");
//        }
//        if (member.getName() == null || member.getName().isBlank()) {
//            throw new BadRequestException("Name cannot be empty");
//        }
//        if (member.getEmail() == null || member.getEmail().isBlank()) {
//            throw new BadRequestException("Email cannot be empty");
//        }
//        if (!member.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
//            throw new BadRequestException("Invalid email format");
//        }

        // Prevent duplicate username/email
        Member byUsername = memberRepository.findByUsername(member.getUsername());
        if (byUsername != null && byUsername.getId() != member.getId()) {
            throw new DuplicateMemberException("Username already taken");
        }

        Member byEmail = memberRepository.findByEmail(member.getEmail());
        if (byEmail != null && byEmail.getId() != member.getId()) {
            throw new DuplicateMemberException("Email already registered");
        }

        try {
            memberRepository.updateMember(member);
            return memberRepository.findById(member.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("The account you are trying to update, does not exit. Please Try again");
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Database error while updating member", e);
        }

    }

    public Member delete(int id) {
        Member existing = getById(id);

        try {
            memberRepository.deleteById(id);
            return existing;
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Database error while deleting member", e);
        }
    }

    /**
     * TODO: Add validation:
     * - username not blank
     * - password not blank
     * <p>
     * TODO: Add error handling:
     * - If findByUsername throws, convert to NotFoundException
     * - If password mismatch, return null or throw BadRequestException
     * <p>
     * TODO: Consider hashing passwords (future improvement)
     */
    public Member login(String username, String password) {
        // TODO: is this checked for on template-level??
//        if (username == null || username.isBlank()) {
//            throw new BadRequestException("Username cannot be empty");
//        }
//        if (password == null || password.isBlank()) {
//            throw new BadRequestException("Password cannot be empty");
//        }

        Member member = memberRepository.findByUsername(username);

        // Username not found
        if (member == null) {
            return null;
        }

        // Password mismatch
        if (!member.getPassword().equals(password)) {
            return null;
        }

        // Success
        return member;
    }
}