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

    /**
     * TODO: Add validation:
     * - email cannot be null/blank
     * - email must contain '@'
     * - Catch repository exceptions and convert to NotFoundException
     */
    public Member getByEmail(String email) {
        try {
            if (email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            }
            return memberRepository.findByEmail(email);
        } catch (Exception e) {
            throw new NotFoundException("No accout with this email found " + email);
        }
    }

    /**
     * TODO: Add validation before inserting:
     * - username not blank
     * - password not blank
     * - email valid format
     * - name not blank
     * <p>
     * TODO: Catch SQL exceptions (duplicate username/email)
     * - Convert to BadRequestException with a friendly message
     */
    @Transactional
    public Member create(Member member) {
        try {
            memberRepository.insertMember(member);
            return memberRepository.findByUsername(member.getUsername());
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateMemberException(e.getMessage());
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("User creation failed", e);
        }
    }

    /**
     * TODO: Add validation:
     * - Same rules as create()
     * - Ensure member exists before updating
     * - Catch SQL exceptions and convert to BadRequestException
     */
    // TODO: For updateMember:
    // 1. Load existing member using getById()
    // 2. Validate username, email, password, name
    // 3. Apply only allowed fields (e.g., do NOT overwrite id)
    // 4. Save using memberRepository.updateMember(existing)
    // 5. Return the updated member
    //
    // Reason: Members have sensitive fields (username, email, password).
    // We must prevent accidental or malicious overwrites.
    @Transactional
    public Member update(Member member) {
        getByEmail(member.getEmail());
        getById(member.getId());
        getByUsername(member.getUsername());

        try {

            memberRepository.updateMember(member);
            return memberRepository.findById(member.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("The account you are trying to update, does not exit. Please Tru again");
        }
    }

    /**
     * TODO: Add error handling:
     * - Ensure member exists before deleting
     * - Convert repository exceptions into NotFoundException
     */
    public Member delete(int id) {
        getById(id);

        try {
            Member deletedMember = memberRepository.findById(id);
            memberRepository.deleteById(id);
            return deletedMember;
        } catch (Exception e) {
            throw new NotFoundException("Delete failed: Could not find user: " + id);
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

        // Find user by username
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